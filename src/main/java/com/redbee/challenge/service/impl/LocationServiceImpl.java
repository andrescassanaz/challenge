package com.redbee.challenge.service.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redbee.challenge.dto.LocationDto;
import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.WeatherHistory;
import com.redbee.challenge.repository.LocationRepository;
import com.redbee.challenge.repository.WeatherHistoryRepository;
import com.redbee.challenge.service.BoardService;
import com.redbee.challenge.service.LocationService;
import com.redbee.challenge.service.WeatherHistoryService;
import com.redbee.challenge.util.RestResponse;
import com.redbee.challenge.util.exception.CityNotFoundException;
import com.redbee.challenge.util.yahoo.api.YahooRestClientService;
import com.redbee.challenge.util.yahoo.api.data.YahooApiResponse;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	YahooRestClientService yahooRestClientService;

	@Autowired
	LocationRepository locationRepository;

	@Autowired
	WeatherHistoryRepository weatherHistoryRepository;

	@Autowired
	BoardService boardService;

	@Autowired
	WeatherHistoryService weatherHistoryService;

	private ObjectMapper mapper = new ObjectMapper();

	private DateFormat dateFormater = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa z", Locale.ENGLISH);

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
	public RestResponse addLocation(String locationJson) {
		RestResponse restResponse;

		try {
			LocationDto locationDto = this.mapper.readValue(locationJson, LocationDto.class);

			Long woeid = yahooRestClientService.getWoeidFromCityName(locationDto.getCity());

			YahooApiResponse yahooApiResponse = yahooRestClientService.getWeatherFromWoeid(woeid);

			if (yahooApiResponse.getQuery().getResults() != null) {

				Location location = buildLocation(woeid, yahooApiResponse);

				Board board = boardService.findBoardById(locationDto.getBoardId());

				Set<Board> boards = boardService.findByUser(board.getUser());
				boards.add(board);
				location.setBoards(boards);

				Location savedLocation = this.save(location);

				WeatherHistory weatherHistoryPoint = buildWeatherHistory(savedLocation, yahooApiResponse);

				WeatherHistory weatherHistoryPointDB = weatherHistoryService.findByLocationAndDate(savedLocation,
						weatherHistoryPoint.getDate());

				if (weatherHistoryPointDB == null) {
					weatherHistoryService.save(weatherHistoryPoint);
				}

				restResponse = new RestResponse(HttpStatus.OK.value(), "Location saved!");

			} else {
				restResponse = new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "No existe ciudad");
			}

		} catch (IOException e) {
			e.printStackTrace();
			restResponse = new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error mapping location");
		} catch (CityNotFoundException e) {
			restResponse = new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ciudad no encontrada");
		} catch (ParseException e) {
			restResponse = new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error en parseo de fecha");
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
	public RestResponse deleteLocation(String locationJson) {
		RestResponse restResponse;
		try {
			LocationDto locationDto = this.mapper.readValue(locationJson, LocationDto.class);
			Location location = this.findById(locationDto.getWoeid());
			if (location != null) {
				this.delete(location);
				restResponse = new RestResponse(HttpStatus.OK.value(), "Location Deleted!");
			} else {
				restResponse = new RestResponse(HttpStatus.CONFLICT.value(), "Nonexistent location!");
			}

		} catch (IOException e) {
			e.printStackTrace();
			restResponse = new RestResponse(HttpStatus.CONFLICT.value(), "Mapping location error");
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

	/**
	 * Builds the weather history.
	 *
	 * @param locationSaved    the location saved
	 * @param yahooApiResponse the yahoo api response
	 * @return Weather History Point
	 * @throws ParseException
	 */
	private WeatherHistory buildWeatherHistory(Location locationSaved, YahooApiResponse yahooApiResponse)
			throws ParseException {

		Calendar date = parseStringToCalendar(
				yahooApiResponse.getQuery().getResults().getChannel().getItem().getCondition().getDate());

		int temp = yahooApiResponse.getQuery().getResults().getChannel().getItem().getCondition().getTemp();
		String description = yahooApiResponse.getQuery().getResults().getChannel().getItem().getCondition().getText();

		WeatherHistory weatherHistory = new WeatherHistory(locationSaved, date.getTimeInMillis(), temp, description);
		return weatherHistory;
	}

	/**
	 * Parses the string to calendar.
	 *
	 * @param dateStr date in string format
	 * @return Calendar
	 * @throws ParseException
	 */
	private Calendar parseStringToCalendar(String dateStr) throws ParseException {
		Date temporalDate = dateFormater.parse(dateStr);
		Calendar date = Calendar.getInstance();
		date.setTime(temporalDate);
		return date;
	}

}
