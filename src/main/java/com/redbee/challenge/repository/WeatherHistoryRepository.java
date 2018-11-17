package com.redbee.challenge.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.WeatherHistory;

@Repository
public interface WeatherHistoryRepository extends JpaRepository<WeatherHistory, Serializable> {

	public WeatherHistory findByLocationAndDate(Location location, long date);

}
	