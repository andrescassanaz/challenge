package com.redbee.challenge.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.WeatherHistory;

@Repository
public interface WeatherHistoryRepository extends JpaRepository<WeatherHistory, Serializable> {

	/**
	 * Find Weather History Point by location and date.
	 *
	 * @param location the location
	 * @param date the date
	 * @return the weather history point
	 */
	public WeatherHistory findByLocationAndDate(Location location, long date);

}
	