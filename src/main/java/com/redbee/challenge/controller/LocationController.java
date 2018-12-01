package com.redbee.challenge.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.redbee.challenge.dto.LocationDto;
import com.redbee.challenge.exception.BoardNotFoundException;
import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.exception.LocationNotFoundException;
import com.redbee.challenge.exception.YahooApiException;
import com.redbee.challenge.service.LocationService;
import com.redbee.challenge.service.WeatherPointService;
import com.redbee.challenge.util.QueryResult;
import com.redbee.challenge.util.RestResponse;
import com.redbee.challenge.util.yahoo.api.YahooRestClientService;

/**
 * @author Andres Cassanaz
 *
 */
@RestController
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
	 * Gets the locations by board.
	 *
	 * @param boardId the board id
	 * @return the locations by board
	 */
	@GetMapping("/locations/{boardId}")
	public QueryResult getLocationsByBoard(@PathVariable String boardId) {
		LOGGER.info("GetMapping: " + "/locations/"+boardId);
		List<LocationDto> locationByBoard = locationService.getLocationsByBoard(boardId);
		RestResponse restResponse = new RestResponse(HttpStatus.OK.value(), "Ok");
		return new QueryResult(restResponse, new ArrayList<Object>(locationByBoard));
	}

	/**
	 * Add a new location.
	 *
	 * @param locationJson the location on json format
	 * @return restResponse
	 */
	@PutMapping("/locations")
	public RestResponse addLocation(@RequestBody String locationJson) {
		LOGGER.info("PutMapping: " + "/locations/ : "+locationJson);
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
		} catch (YahooApiException e) {
			restResponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: YahooApiException");
			LOGGER.error(restResponse.getMessage());
		}
		return restResponse;
	}

	/**
	 * Delete location of board.
	 *
	 * @param boardId the board id
	 * @param woeid the woeid of city
	 * @return a RestResponse
	 */
	@DeleteMapping("/locations/{boardId}/{woeid}")
	public RestResponse deleteLocationOfBoard(@PathVariable String boardId, @PathVariable String woeid) {
		LOGGER.info("DeleteMapping: " + "/"+boardId+"/"+woeid);
		RestResponse restResponse;
		try {
			locationService.deleteLocationOfBoard(boardId,  woeid);
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
