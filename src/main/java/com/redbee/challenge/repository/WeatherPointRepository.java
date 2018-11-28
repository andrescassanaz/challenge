package com.redbee.challenge.repository;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.WeatherPoint;

/**
 * @author Andres Cassanaz
 *
 */
@Repository
public interface WeatherPointRepository extends JpaRepository<WeatherPoint, Serializable> {

	/**
	 * Find WeatherPoint by location and date.
	 *
	 * @param location the location
	 * @param date     the date
	 * @return the weather point
	 */
	public WeatherPoint findByLocationAndDate(Location location, long date);

	/**
	 * Find WeatherPoints by location and date.
	 *
	 * @param location      the location
	 * @param startOfTheDay the start of the day
	 * @param endOfTheDay   the end of the day
	 * @return the weather points
	 */
	@Query("SELECT wp FROM WeatherPoint wp WHERE id_location = ?1 and date between ?2 and ?3 ORDER BY date asc")
	public Set<WeatherPoint> findByLocationAndDate(Location location, long startOfTheDay, long endOfTheDay);

	/**
	 * Find WeatherPoints by location.
	 *
	 * @param location the location
	 * @return the weatherpoints
	 */
	public Set<WeatherPoint> findByLocation(Location location);

	/**
	 * Find last weatherpoint of location
	 *
	 * @param location the location
	 * @return the weather point
	 */
	public WeatherPoint findFirstByLocationOrderByDateDesc(Location location);

}
