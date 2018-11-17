package com.redbee.challenge.service.impl;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.User;
import com.redbee.challenge.model.WeatherHistory;
import com.redbee.challenge.repository.WeatherHistoryRepository;
import com.redbee.challenge.service.WeatherHistoryService;

@Service
public class WeatherHistoryServiceImpl implements WeatherHistoryService {

	@Autowired
	WeatherHistoryRepository weatherHistoryRepository;

	@Override
	public WeatherHistory save(WeatherHistory weatherHistory) {
		return weatherHistoryRepository.save(weatherHistory);
	}

	@Override
	public WeatherHistory findByLocationAndDate(Location location, Long date) {
		return weatherHistoryRepository.findByLocationAndDate(location, date);
	}

}
