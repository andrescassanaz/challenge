package com.redbee.challenge.service;

import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.WeatherHistory;

public interface WeatherHistoryService {

	public WeatherHistory save(WeatherHistory weatherHistory);

	public WeatherHistory findByLocationAndDate(Location locationSaved, Long date);
	
}
