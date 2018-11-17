package com.redbee.challenge.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redbee.challenge.dto.LocationDto;
import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.WeatherHistory;
import com.redbee.challenge.service.BoardService;
import com.redbee.challenge.service.LocationService;
import com.redbee.challenge.service.WeatherHistoryService;
import com.redbee.challenge.util.RestResponse;
import com.redbee.challenge.util.exception.CityNotFoundException;
import com.redbee.challenge.util.yahoo.api.YahooRestClientService;
import com.redbee.challenge.util.yahoo.api.data.YahooApiResponse;

@RestController
public class LocationController {

	@Autowired
	private LocationService locationService;

	@Autowired
	private BoardService boardService;

	@Autowired
	WeatherHistoryService weatherHistoryService;

	@Autowired
	YahooRestClientService yahooRestClientService;

	private ObjectMapper mapper = new ObjectMapper();

	@PostMapping("/location/save")
	public RestResponse saveOrUpdate(@RequestBody String locationJson) {
		RestResponse restResponse;
		try {
			LocationDto locationDto = this.mapper.readValue(locationJson, LocationDto.class);

			Long woeid = yahooRestClientService.getWoeidFromCityName(locationDto.getCity());
			YahooApiResponse yahooApiResponse = yahooRestClientService.getWeatherFromWoeid(woeid);

			if (yahooApiResponse.getQuery().getResults() != null) {

				String city = yahooApiResponse.getQuery().getResults().getChannel().getLocation().getCity();
				String country = yahooApiResponse.getQuery().getResults().getChannel().getLocation().getCountry();
				String date = yahooApiResponse.getQuery().getResults().getChannel().getItem().getCondition().getDate();
				int temp = yahooApiResponse.getQuery().getResults().getChannel().getItem().getCondition().getTemp();
				String description = yahooApiResponse.getQuery().getResults().getChannel().getItem().getCondition()
						.getText();

				Location location = new Location(woeid, city, country);

				Board board = boardService.findBoardById(locationDto.getBoardId());

				Set<Board> boards = new HashSet<Board>();
				boards.add(board);
				location.setBoards(boards);

				Location locationSaved = locationService.save(location);

				WeatherHistory weatherHistory = new WeatherHistory(locationSaved, date, temp, description);

				weatherHistoryService.save(weatherHistory);

				restResponse = new RestResponse(HttpStatus.OK.value(), "Location saved!");

			} else {
				restResponse = new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "No existe ciudad");
			}

		} catch (IOException e) {
			e.printStackTrace();
			restResponse = new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error mapping location");
		} catch (CityNotFoundException e) {
			restResponse = new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ciudad no encontrada");
		}
		return restResponse;

	}

	@PostMapping("/location/delete")
	public RestResponse delete(@RequestBody String locationJson) {
		RestResponse restResponse;
		try {
			LocationDto locationDto = this.mapper.readValue(locationJson, LocationDto.class);
			Location location = locationService.findById(locationDto.getWoeid());
			if (location != null) {
				locationService.delete(location);
				restResponse = new RestResponse(HttpStatus.OK.value(), "Location Deleted!");
			} else {
				restResponse = new RestResponse(HttpStatus.CONFLICT.value(), "Nonexistent location!");
			}

		} catch (IOException e) {
			e.printStackTrace();
			restResponse = new RestResponse(HttpStatus.CONFLICT.value(), "Error mapping location");
		}
		return restResponse;

	}

}
