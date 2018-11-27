package com.redbee.challenge.repository;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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

	//@Query("SELECT * FROM weather_point WHERE id_location = #{location} and date between #{startOfTheDay} and #{endOfTheDay}")
	@Query("SELECT wp FROM WeatherPoint wp WHERE id_location = ?1 and date between ?2 and ?3 ORDER BY date asc")
	public Set<WeatherPoint> findByLocationAndDate(Location location, long startOfTheDay, long endOfTheDay);
	
	public Set<WeatherPoint> findByLocation(Location location);
	
	public WeatherPoint findFirstByLocationOrderByDateDesc(Location location);

}
	