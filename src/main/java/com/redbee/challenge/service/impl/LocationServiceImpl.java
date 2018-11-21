package com.redbee.challenge.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
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
import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.WeatherPoint;
import com.redbee.challenge.repository.LocationRepository;
import com.redbee.challenge.service.BoardService;
import com.redbee.challenge.service.LocationService;
import com.redbee.challenge.service.WeatherPointService;
import com.redbee.challenge.util.RestResponse;
import com.redbee.challenge.util.exception.BoardNotFoundException;
import com.redbee.challenge.util.exception.CityNotFoundException;
import com.redbee.challenge.util.exception.LocationNotFoundException;
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
	 * @see
	 * com.redbee.challenge.service.LocationService#delete(com.redbee.challenge.
	 * model.Location)
	 */
	@Override
	public void delete(Location location) {
		locationRepository.delete(location);

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

		if (yahooApiResponse.getQuery().getResults() != null) {

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

		} else {
			restResponse = new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "No existe ciudad");
		}

		return restResponse;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.redbee.challenge.service.LocationService#deleteLocation(java.lang.String)
	 */
	@Override
	public RestResponse deleteLocation(String locationJson)
			throws JsonParseException, JsonMappingException, IOException {
		RestResponse restResponse;

		LocationDto locationDto = this.mapper.readValue(locationJson, LocationDto.class);
		Location location = this.findById(locationDto.getWoeid());
		if (location != null) {
			this.delete(location);
			restResponse = new RestResponse(HttpStatus.OK.value(), "Location Deleted!");
		} else {
			restResponse = new RestResponse(HttpStatus.CONFLICT.value(), "Nonexistent location!");
		}

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
	public Location deleteLocationOfBoard(String locationJson) throws JsonParseException, JsonMappingException, IOException, BoardNotFoundException, LocationNotFoundException {
		LocationDto locationDto = this.mapper.readValue(locationJson, LocationDto.class);
		Location location = this.findById(locationDto.getWoeid());

		Board boardToDelete = new Board();;
		for(Board board: location.getBoards()) {
			if(board.getId() == locationDto.getBoardId()) {
				boardToDelete = board;
			}
		}
		if(boardToDelete.getId() == null) {
			throw new BoardNotFoundException();
		}
		location.getBoards().remove(boardToDelete);
		locationRepository.save(location);
		return location;
	}

}
