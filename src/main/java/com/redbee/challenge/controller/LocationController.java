package com.redbee.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.redbee.challenge.service.LocationService;
import com.redbee.challenge.service.WeatherPointService;
import com.redbee.challenge.util.RestResponse;
import com.redbee.challenge.util.yahoo.api.YahooRestClientService;

@RestController
public class LocationController {

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
		RestResponse restResponse = locationService.addLocation(locationJson);
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
		RestResponse restResponse = locationService.deleteLocation(locationJson);
		return restResponse;
	}

}
