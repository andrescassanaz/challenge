package com.redbee.challenge.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.WeatherHistory;
import com.redbee.challenge.repository.LocationRepository;
import com.redbee.challenge.repository.WeatherHistoryRepository;
import com.redbee.challenge.service.LocationService;
import com.redbee.challenge.util.yahoo.api.YahooRestClientService;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	YahooRestClientService yahooRestClientService;

	@Autowired
	LocationRepository locationRepository;

	@Autowired
	WeatherHistoryRepository weatherHistoryRepository;

	@Override
	public Location save(Location location) {
		return locationRepository.save(location);
	}

	@Override
	public void delete(Location location) {
		locationRepository.delete(location);

	}

	@Override
	public Location findById(long id) {
		Optional<Location> location = locationRepository.findById(id);
		if(location.isPresent()) {
			return location.get();
		} else {
			return null;
		}

	}

}
