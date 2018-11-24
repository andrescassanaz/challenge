package com.redbee.challenge.repository;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.WeatherPoint;

@Repository
public interface WeatherPointRepository extends JpaRepository<WeatherPoint, Serializable> {

	/**
	 * Find Weather History Point by location and date.
	 *
	 * @param location the location
	 * @param date the date
	 * @return the weather history point
	 */
	public WeatherPoint findByLocationAndDate(Location location, long date);
	
	public Set<WeatherPoint> findByLocation(Location location);
	
	public WeatherPoint findFirstByLocationOrderByDateDesc(Location location);

}
	