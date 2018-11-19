package com.redbee.challenge.controller;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.redbee.challenge.service.LocationService;
import com.redbee.challenge.service.WeatherPointService;
import com.redbee.challenge.util.RestResponse;
import com.redbee.challenge.util.exception.CityNotFoundException;
import com.redbee.challenge.util.yahoo.api.YahooRestClientService;

@RestController
public class LocationController {

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
		RestResponse restReponse;
		try {
			locationService.addLocation(locationJson);
			restReponse = new RestResponse(HttpStatus.OK.value(), "Ok");
		} catch (JsonParseException e) {
			restReponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: JsonParseException");
		} catch (JsonMappingException e) {
			restReponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: JsonMappingException");
		} catch (IOException e) {
			restReponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: IOException");
		} catch (CityNotFoundException e) {
			restReponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: CityNotFoundException");
		} catch (ParseException e) {
			restReponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: ParseException");
		}
		return restReponse;
	}

	/**
	 * Delete.
	 *
	 * @param locationJson the location on json format
	 * @return restResponse
	 */
	@PostMapping("/deleteLocation")
	public RestResponse delete(@RequestBody String locationJson) {
		RestResponse restReponse;
		try {
			locationService.deleteLocation(locationJson);
			restReponse = new RestResponse(HttpStatus.OK.value(), "Ok");
		} catch (JsonParseException e) {
			restReponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: JsonParseException");
		} catch (JsonMappingException e) {
			restReponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: JsonMappingException");
		} catch (IOException e) {
			restReponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: IOException");
		}
		return restReponse;

	}

}
