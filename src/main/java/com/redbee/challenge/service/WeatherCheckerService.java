package com.redbee.challenge.service;

import java.text.ParseException;

import com.redbee.challenge.exception.CityNotFoundException;

public interface WeatherCheckerService {

	void startWeatherChecker() throws ParseException, CityNotFoundException;

}
