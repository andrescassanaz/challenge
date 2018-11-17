package com.redbee.challenge.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
