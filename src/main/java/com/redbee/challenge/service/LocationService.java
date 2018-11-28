package com.redbee.challenge.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.redbee.challenge.dto.LocationDto;
import com.redbee.challenge.exception.BoardNotFoundException;
import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.exception.LocationNotFoundException;
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
	 * Find by id.
	 *
	 * @param id the id
	 * @return the location
	 */
	public Location findById(long id);

	/**
	 * Add a new location.
	 *
	 * @param locationJson the location on json format
	 * @return restResonse
	 * @throws JsonParseException json parse exception
	 * @throws JsonMappingException json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws CityNotFoundException city not found exception
	 * @throws ParseException parse exception
	 */ 
	public RestResponse addLocation(String locationJson) throws JsonParseException, JsonMappingException, IOException, CityNotFoundException, ParseException;

	/**
	 * Delete location.
	 *
	 * @param boardId the board id
	 * @param woeid the woeid of city
	 * @return restResonse
	 * @throws JsonParseException json parse exception
	 * @throws JsonMappingException json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws BoardNotFoundException board not found exception
	 * @throws LocationNotFoundException location not found exception
	 */

	public Location deleteLocationOfBoard(String boardId,String  woeid) throws JsonParseException, JsonMappingException, IOException, BoardNotFoundException, LocationNotFoundException;
	
	/**
	 * Find all.
	 *
	 * @return the list of all locations
	 */
	public List<Location> findAll();

	/**
	 * Gets the locations by board.
	 *
	 * @param boardId the board id
	 * @return the locations by board
	 */
	public List<LocationDto> getLocationsByBoard(String boardId);
	

}
