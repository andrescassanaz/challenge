package com.redbee.challenge.service;

import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.WeatherHistory;

public interface WeatherHistoryService {

	/**
	 * Save.
	 *
	 * @param weatherHistory the weather history
	 * @return the saved weather history
	 */
	public WeatherHistory save(WeatherHistory weatherHistory);

	/**
	 * Find by location and date.
	 *
	 * @param savedLocation the saved location 
	 * @param date the date
	 * @return the weather history
	 */
	public WeatherHistory findByLocationAndDate(Location savedLocation, Long date);
	
}
