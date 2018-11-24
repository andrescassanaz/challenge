package com.redbee.challenge.service;

import java.text.ParseException;

import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.WeatherPoint;
import com.redbee.challenge.util.yahoo.api.data.YahooApiResponse;

public interface WeatherPointService {

	/**
	 * Save.
	 *
	 * @param weatherPoint the weather history
	 * @return the saved weather history
	 */
	public WeatherPoint save(WeatherPoint weatherPoint);

	/**
	 * Find by location and date.
	 *
	 * @param savedLocation the saved location
	 * @param date          the date
	 * @return the weather history
	 */
	public WeatherPoint findByLocationAndDate(Location savedLocation, Long date);

	public WeatherPoint buildWeatherPoint(Location locationSaved, YahooApiResponse yahooApiResponse) throws ParseException;

	public WeatherPoint saveIfNecessary(WeatherPoint weatherPoint);
	
	public WeatherPoint updateWeatherPointOfLocation(Location location) throws ParseException, CityNotFoundException;
	
	
	
}
