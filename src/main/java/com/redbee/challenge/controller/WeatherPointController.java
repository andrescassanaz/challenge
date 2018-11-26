package com.redbee.challenge.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.redbee.challenge.dto.WeatherPointDto;
import com.redbee.challenge.model.WeatherPoint;
import com.redbee.challenge.service.WeatherPointService;
import com.redbee.challenge.util.QueryResult;
import com.redbee.challenge.util.RestResponse;

@RestController
public class WeatherPointController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(WeatherPointController.class);
	
	@Autowired
	WeatherPointService weatherPointService;
	
	
//	@GetMapping("/weatherpoints/{woeid}")
//	public QueryResult getWeatherPointsByLocation(@PathVariable String woeid) {
//		List<WeatherPointDto> weatherpointsByBoard = weatherPointService.getWeatherPointByLocation(woeid);
//		RestResponse restResponse = new RestResponse(HttpStatus.OK.value(), "Ok");
//		return new QueryResult(restResponse, new ArrayList<Object>(weatherpointsByBoard));
//	}
	@GetMapping("/weatherpoints/{woeid}")
	public QueryResult getWeatherPointsByLocation(@PathVariable String woeid) {
		WeatherPointDto lastWeatherPoint = weatherPointService.getLastWeatherPointByLocation(woeid);
		List<WeatherPointDto> weatherPoints  = new ArrayList<WeatherPointDto>();
		weatherPoints.add(lastWeatherPoint);
		RestResponse restResponse = new RestResponse(HttpStatus.OK.value(), "Ok");
		return new QueryResult(restResponse, new ArrayList<Object>(weatherPoints));
	}

}
