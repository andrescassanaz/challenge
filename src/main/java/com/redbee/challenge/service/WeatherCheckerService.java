package com.redbee.challenge.service;

import java.text.ParseException;

import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.exception.YahooApiException;

/**
 * @author Andres Cassanaz
 *
 */
public interface WeatherCheckerService {

	/**
	 * Start weather checker.
	 *
	 * @throws ParseException the parse exception
	 * @throws CityNotFoundException the city not found exception
	 * @throws YahooApiException 
	 */
	void startWeatherChecker() throws ParseException, CityNotFoundException, YahooApiException;

}
