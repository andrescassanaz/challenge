package com.redbee.challenge.controller;

import java.io.IOException;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.redbee.challenge.service.LocationService;
import com.redbee.challenge.service.WeatherPointService;
import com.redbee.challenge.util.RestResponse;
import com.redbee.challenge.util.exception.BoardNotFoundException;
import com.redbee.challenge.util.exception.CityNotFoundException;
import com.redbee.challenge.util.exception.LocationNotFoundException;
import com.redbee.challenge.util.yahoo.api.YahooRestClientService;

@RestController
@CrossOrigin
public class LocationController {

	private static Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

	private static int INTERNAL_SERVER_ERRROR = HttpStatus.INTERNAL_SERVER_ERROR.value();

	@Autowired
	private LocationService locationService;

	@Autowired
	WeatherPointService weatherPointService;

	@Autowired
	YahooRestClientService yahooRestClientService;

	/**
	 * Adds a new location.
	 *
	 * @param locationJson the location on json format
	 * @return restResponse
	 */
	@PostMapping("/addLocation")
	public RestResponse addLocation(@RequestBody String locationJson) {
		RestResponse restResponse;
		try {
			locationService.addLocation(locationJson);
			restResponse = new RestResponse(HttpStatus.OK.value(), "Ok");
		} catch (JsonParseException e) {
			restResponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: JsonParseException");
			LOGGER.error(restResponse.getMessage());
		} catch (JsonMappingException e) {
			restResponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: JsonMappingException");
			LOGGER.error(restResponse.getMessage());
		} catch (IOException e) {
			restResponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: IOException");
			LOGGER.error(restResponse.getMessage());
		} catch (CityNotFoundException e) {
			restResponse = new RestResponse(INTERNAL_SERVER_ERRROR, "City not found");
			LOGGER.error(restResponse.getMessage());
		} catch (ParseException e) {
			restResponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: ParseException");
			LOGGER.error(restResponse.getMessage());
		}
		return restResponse;
	}

	/**
	 * Delete.
	 *
	 * @param locationJson the location on json format
	 * @return restResponse
	 */
	@PostMapping("/deleteLocation")
	public RestResponse delete(@RequestBody String locationJson) {
		RestResponse restResponse;
		try {
			locationService.deleteLocation(locationJson);
			restResponse = new RestResponse(HttpStatus.OK.value(), "Ok");
		} catch (JsonParseException e) {
			restResponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: JsonParseException");
			LOGGER.error(restResponse.getMessage());
		} catch (JsonMappingException e) {
			restResponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: JsonMappingException");
			LOGGER.error(restResponse.getMessage());
		} catch (IOException e) {
			restResponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: IOException");
			LOGGER.error(restResponse.getMessage());
		}
		return restResponse;

	}

	@PostMapping("/deleteLocationOfBoard")
	public RestResponse deleteLocationOfBoard(@RequestBody String boardJson) {
		RestResponse restResponse;
		try {
			locationService.deleteLocationOfBoard(boardJson);
			restResponse = new RestResponse(HttpStatus.OK.value(), "Ok");
		} catch (JsonParseException e) {
			restResponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: JsonParseException");
			LOGGER.error(restResponse.getMessage());
		} catch (JsonMappingException e) {
			restResponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: JsonMappingException");
			LOGGER.error(restResponse.getMessage());
		} catch (IOException e) {
			restResponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: IOException");
			LOGGER.error(restResponse.getMessage());
		} catch (BoardNotFoundException e) {
			restResponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: Board not found");
			LOGGER.error(restResponse.getMessage());
		} catch (LocationNotFoundException e) {
			restResponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: Location not found");
			LOGGER.error(restResponse.getMessage());
		}
		return restResponse;
	}

}
