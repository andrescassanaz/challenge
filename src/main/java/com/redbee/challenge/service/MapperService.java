package com.redbee.challenge.service;

import com.redbee.challenge.dto.BoardDto;
import com.redbee.challenge.dto.LocationDto;
import com.redbee.challenge.dto.UserDto;
import com.redbee.challenge.dto.WeatherPointDto;
import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.User;
import com.redbee.challenge.model.WeatherPoint;

public interface MapperService {
	
	public WeatherPointDto mapWeatherPointToDto (WeatherPoint weatherPoint);
	
	public LocationDto mapLocationToDto (Location location);
	
	public UserDto mapUserToDto (User user);
	
	public BoardDto mapBoardToDto (Board board);
	

}
