package com.redbee.challenge.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.redbee.challenge.dto.WeatherPointDto;
import com.redbee.challenge.service.WeatherPointService;
import com.redbee.challenge.util.QueryResult;
import com.redbee.challenge.util.RestResponse;

/**
 * @author Andres Cassanaz
 *
 */
@RestController
public class WeatherPointController {

	private static Logger LOGGER = LoggerFactory.getLogger(WeatherPointController.class);

	@Autowired
	WeatherPointService weatherPointService;

	/**
	 * Gets the weather points by location.
	 *
	 * @param woeid the woeid of city
	 * @return the weather points by location
	 */
	@GetMapping("/weatherpoints/{woeid}")
	public QueryResult getWeatherPointsByLocation(@PathVariable String woeid) {
		LOGGER.info("GetMapping: " + "weatherpoints/"+woeid);
		WeatherPointDto lastWeatherPoint = weatherPointService.getLastWeatherPointByLocation(woeid);
		List<WeatherPointDto> weatherPoints = new ArrayList<WeatherPointDto>();
		weatherPoints.add(lastWeatherPoint);
		RestResponse restResponse = new RestResponse(HttpStatus.OK.value(), "Ok");
		return new QueryResult(restResponse, new ArrayList<Object>(weatherPoints));
	}

	/**
	 * Gets the weather points by location and date.
	 *
	 * @param woeid the woeid
	 * @param date  the date
	 * @return the weather points by location and date
	 */
	@GetMapping("/weatherpoints/{woeid}/{date}")
	public QueryResult getWeatherPointsByLocationAndDate(@PathVariable String woeid, @PathVariable Long date) {
		LOGGER.info("GetMapping: " + "weatherpoints/"+woeid+"/"+date);
		List<WeatherPointDto> weatherPointByLocationAndDate = weatherPointService
				.getWeatherPointByLocationAndDate(woeid, date);
		RestResponse restResponse = new RestResponse(HttpStatus.OK.value(), "Ok");
		return new QueryResult(restResponse, new ArrayList<Object>(weatherPointByLocationAndDate));
	}

}
