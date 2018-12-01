package com.redbee.challenge.service;

import java.text.ParseException;
import java.util.List;

import com.redbee.challenge.dto.WeatherPointDto;
import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.exception.YahooApiCallLimitExceededException;
import com.redbee.challenge.exception.YahooApiException;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.WeatherPoint;
import com.redbee.challenge.util.yahoo.api.data.YahooApiResponse;

/**
 * @author Andres Cassanaz
 *
 */
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

	/**
	 * Builds the weather point.
	 *
	 * @param locationSaved the location
	 * @param yahooApiResponse the yahoo api response
	 * @return the weather point
	 * @throws ParseException the parse exception
	 */
	public WeatherPoint buildWeatherPoint(Location locationSaved, YahooApiResponse yahooApiResponse) throws ParseException;

	/**
	 * Save WeatherPoint only if there was an update.
	 *
	 * @param weatherPoint the weather point
	 * @return the weather point
	 */
	public WeatherPoint saveIfNecessary(WeatherPoint weatherPoint);
	
	/**
	 * Update weather point of location.
	 *
	 * @param location the location
	 * @return the weather point
	 * @throws ParseException the parse exception
	 * @throws CityNotFoundException the city not found exception
	 * @throws YahooApiException 
	 * @throws YahooApiCallLimitExceededException 
	 */
	public WeatherPoint updateWeatherPointOfLocation(Location location) throws ParseException, CityNotFoundException, YahooApiException, YahooApiCallLimitExceededException;

	/**
	 * Gets the weather point by location.
	 *
	 * @param woeid the woeid
	 * @return the weather point by location
	 */
	public List<WeatherPointDto> getWeatherPointByLocation(String woeid);
	
	/**
	 * Gets the weather point by location and date.
	 *
	 * @param woeid the woeid
	 * @param timeInMilliseconds the time in milliseconds
	 * @return the weather point by location and date
	 */
	public List<WeatherPointDto> getWeatherPointByLocationAndDate(String woeid, Long timeInMilliseconds);
	
	/**
	 * Gets the last weather point by location.
	 *
	 * @param woeid the woeid
	 * @return the last weather point by location
	 */
	public WeatherPointDto getLastWeatherPointByLocation(String woeid);
	
	
	
	
}
