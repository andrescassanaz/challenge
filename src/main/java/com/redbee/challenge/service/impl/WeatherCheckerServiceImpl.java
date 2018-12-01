package com.redbee.challenge.service.impl;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.exception.YahooApiException;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.service.LocationService;
import com.redbee.challenge.service.WeatherCheckerService;
import com.redbee.challenge.service.WeatherPointService;

/**
 * @author Andres Cassanaz
 *
 */
@Service
public class WeatherCheckerServiceImpl implements WeatherCheckerService {

	private static Logger LOGGER = LoggerFactory.getLogger(WeatherCheckerServiceImpl.class);

	@Autowired
	LocationService locationService;

	@Autowired
	WeatherPointService weatherPointService;

	@Override
	public void startWeatherChecker() throws YahooApiException {
		List<Location> locations = locationService.findAll();
		for (Location location : locations) {
			try {
				weatherPointService.updateWeatherPointOfLocation(location);
			} catch (ParseException e) {
				LOGGER.error("WeatherChecker -> ParseException -> WOEID: " + location.getWoeid());
			} catch (CityNotFoundException e) {
				LOGGER.error("WeatherChecker -> CityNotFoundException -> WOEID: " + location.getWoeid());
			}
		}
	}

}
