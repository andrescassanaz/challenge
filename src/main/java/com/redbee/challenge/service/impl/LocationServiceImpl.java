package com.redbee.challenge.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redbee.challenge.dto.LocationDto;
import com.redbee.challenge.exception.BoardNotFoundException;
import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.exception.LocationNotFoundException;
import com.redbee.challenge.exception.YahooApiCallLimitExceededException;
import com.redbee.challenge.exception.YahooApiException;
import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.WeatherPoint;
import com.redbee.challenge.repository.LocationRepository;
import com.redbee.challenge.service.BoardService;
import com.redbee.challenge.service.LocationService;
import com.redbee.challenge.service.MapperService;
import com.redbee.challenge.service.WeatherPointService;
import com.redbee.challenge.util.RestResponse;
import com.redbee.challenge.util.yahoo.api.YahooRestClientService;
import com.redbee.challenge.util.yahoo.api.data.YahooApiResponse;

/**
 * @author Andres Cassanaz
 *
 */
@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	YahooRestClientService yahooRestClientService;

	@Autowired
	LocationRepository locationRepository;

	@Autowired
	BoardService boardService;

	@Autowired
	WeatherPointService weatherPointService;

	@Autowired
	MapperService mapperService;

	private ObjectMapper mapper = new ObjectMapper();

	private static Logger LOGGER = LoggerFactory.getLogger(LocationServiceImpl.class);

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Location save(Location location) {
		return locationRepository.save(location);
	}

	@Override
	public Location findById(long id) {
		LOGGER.info("findById: " + id);
		Optional<Location> location = locationRepository.findById(id);
		if (location.isPresent()) {
			return location.get();
		} else {
			return null;
		}

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RestResponse addLocation(String locationJson)
			throws JsonParseException, JsonMappingException, IOException, CityNotFoundException, ParseException, YahooApiException, YahooApiCallLimitExceededException {

		LOGGER.info("addLocation: " + locationJson);
		RestResponse restResponse;

		LocationDto locationDto = this.mapper.readValue(locationJson, LocationDto.class);

		Long woeid = yahooRestClientService.getWoeidFromCityName(locationDto.getCity());
		YahooApiResponse yahooApiResponse = yahooRestClientService.getWeatherFromWoeid(woeid);

		Location location = buildLocation(woeid, yahooApiResponse);

		Board board = boardService.findBoardById(locationDto.getBoardId());

		Set<Location> locations = new HashSet<Location>();
		locations.add(location);
		Set<Board> boards = boardService.findByLocations(locations);

		boards.add(board);
		location.setBoards(boards);

		Location savedLocation = this.save(location);

		WeatherPoint weatherPoint = weatherPointService.buildWeatherPoint(savedLocation, yahooApiResponse);

		weatherPointService.saveIfNecessary(weatherPoint);

		restResponse = new RestResponse(HttpStatus.OK.value(), "Location saved!");

		return restResponse;

	}

	/**
	 * Builds the location.
	 *
	 * @param woeid            the woeid of city
	 * @param yahooApiResponse the yahoo api response
	 * @return the location
	 */
	private Location buildLocation(Long woeid, YahooApiResponse yahooApiResponse) {
		String city = yahooApiResponse.getQuery().getResults().getChannel().getLocation().getCity();
		String country = yahooApiResponse.getQuery().getResults().getChannel().getLocation().getCountry();
		return new Location(woeid, city, country);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Location deleteLocationOfBoard(String boardId, String woeid) throws JsonParseException, JsonMappingException,
			IOException, BoardNotFoundException, LocationNotFoundException {
		LOGGER.info("deleteLocationOfBoard: " + "board: " + boardId + " - woeid: " + woeid);
		Location location = this.findById(Long.parseLong(woeid));

		Board boardToDelete = new Board();
		;
		for (Board board : location.getBoards()) {
			if (board.getId() == Long.parseLong(boardId)) {
				boardToDelete = board;
			}
		}
		if (boardToDelete.getId() == null) {
			throw new BoardNotFoundException();
		}
		location.getBoards().remove(boardToDelete);
		locationRepository.save(location);
		return location;
	}

	@Override
	public List<Location> findAll() {
		LOGGER.info("findAll()");
		return locationRepository.findAll();
	}

	@Override
	public List<LocationDto> getLocationsByBoard(String boardId) {
		LOGGER.info("getLocationsByBoard: " + "board: " + boardId);
		Board board = boardService.findBoardById(Long.parseLong(boardId));
		Set<Board> boards = new HashSet<Board>();
		boards.add(board);
		Set<Location> locationsDb = locationRepository.findByBoards(boards);
		List<LocationDto> locationsDto = new ArrayList<LocationDto>();
		for (Location location : locationsDb) {
			LocationDto locationDto = mapperService.mapLocationToDto(location);
			locationsDto.add(locationDto);
		}
		return locationsDto;
	}

}
