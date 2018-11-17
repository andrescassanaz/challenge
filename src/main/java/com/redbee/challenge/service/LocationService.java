package com.redbee.challenge.service;

import com.redbee.challenge.model.Location;
import com.redbee.challenge.util.RestResponse;

public interface LocationService {

	/**
	 * Save.
	 *
	 * @param location the location
	 * @return the saved location
	 */
	public Location save(Location location);

	/**
	 * Delete.
	 *
	 * @param location the location
	 */
	public void delete(Location location);

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the location
	 */
	public Location findById(long id);

	/**
	 * Adds the location.
	 *
	 * @param locationJson the location on json format
	 * @return restResonse
	 */
	public RestResponse addLocation(String locationJson);

	/**
	 * Delete location.
	 *
	 * @param locationJson the location on json format
	 * @return restResonse
	 */
	public RestResponse deleteLocation(String locationJson);

}
