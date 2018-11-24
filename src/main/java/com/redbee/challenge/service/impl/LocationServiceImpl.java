package com.redbee.challenge.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redbee.challenge.dto.BoardDto;
import com.redbee.challenge.dto.LocationDto;
import com.redbee.challenge.exception.BoardNotFoundException;
import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.exception.LocationNotFoundException;
import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.User;
import com.redbee.challenge.model.WeatherPoint;
import com.redbee.challenge.repository.LocationRepository;
import com.redbee.challenge.service.BoardService;
import com.redbee.challenge.service.LocationService;
import com.redbee.challenge.service.MapperService;
import com.redbee.challenge.service.WeatherPointService;
import com.redbee.challenge.util.RestResponse;
import com.redbee.challenge.util.yahoo.api.YahooRestClientService;
import com.redbee.challenge.util.yahoo.api.data.YahooApiResponse;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.redbee.challenge.service.LocationService#save(com.redbee.challenge.model.
	 * Location)
	 */
	@Override
	public Location save(Location location) {
		return locationRepository.save(location);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redbee.challenge.service.LocationService#findById(long)
	 */
	@Override
	public Location findById(long id) {
		Optional<Location> location = locationRepository.findById(id);
		if (location.isPresent()) {
			return location.get();
		} else {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.redbee.challenge.service.LocationService#addLocation(java.lang.String)
	 */
	@Override
	public RestResponse addLocation(String locationJson)
			throws JsonParseException, JsonMappingException, IOException, CityNotFoundException, ParseException {
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
	public Location deleteLocationOfBoard(String boardId,String woeid) throws JsonParseException, JsonMappingException,
			IOException, BoardNotFoundException, LocationNotFoundException {
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
		return locationRepository.findAll();
	}

	@Override
	public List<LocationDto> getLocationsByBoard(String boardId) {

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
