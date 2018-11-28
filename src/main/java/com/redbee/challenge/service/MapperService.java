package com.redbee.challenge.service;

import com.redbee.challenge.dto.BoardDto;
import com.redbee.challenge.dto.LocationDto;
import com.redbee.challenge.dto.UserDto;
import com.redbee.challenge.dto.WeatherPointDto;
import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.User;
import com.redbee.challenge.model.WeatherPoint;

/**
 * @author Andres Cassanaz
 *
 */
public interface MapperService {
	
	/**
	 * Map weather point to dto.
	 *
	 * @param weatherPoint the weather point
	 * @return the weather point dto
	 */
	public WeatherPointDto mapWeatherPointToDto (WeatherPoint weatherPoint);
	
	/**
	 * Map location to dto.
	 *
	 * @param location the location
	 * @return the location dto
	 */
	public LocationDto mapLocationToDto (Location location);
	
	/**
	 * Map user to dto.
	 *
	 * @param user the user
	 * @return the user dto
	 */
	public UserDto mapUserToDto (User user);
	
	/**
	 * Map board to dto.
	 *
	 * @param board the board
	 * @return the board dto
	 */
	public BoardDto mapBoardToDto (Board board);
	
	

}
