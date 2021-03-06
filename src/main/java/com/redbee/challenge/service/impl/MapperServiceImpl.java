package com.redbee.challenge.service.impl;

import org.springframework.stereotype.Service;

import com.redbee.challenge.dto.BoardDto;
import com.redbee.challenge.dto.LocationDto;
import com.redbee.challenge.dto.UserDto;
import com.redbee.challenge.dto.WeatherPointDto;
import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.User;
import com.redbee.challenge.model.WeatherPoint;
import com.redbee.challenge.service.MapperService;

/**
 * @author Andres Cassanaz
 *
 */
@Service
public class MapperServiceImpl implements MapperService {

	@Override
	public WeatherPointDto mapWeatherPointToDto(WeatherPoint weatherPoint) {
		WeatherPointDto dto = new WeatherPointDto();
		dto.setDate(weatherPoint.getDate());
		dto.setDescription(weatherPoint.getDescription());
		dto.setId(weatherPoint.getId());
		dto.setTemp(weatherPoint.getTemp());
		dto.setCode(weatherPoint.getCode());
		return dto;
	}

	@Override
	public LocationDto mapLocationToDto(Location location) {
		LocationDto dto = new LocationDto();
		dto.setCity(location.getCity());
		dto.setCountry(location.getCountry());
		dto.setWoeid(location.getWoeid());
		return dto;

	}

	@Override
	public UserDto mapUserToDto(User user) {
		UserDto dto = new UserDto();
		dto.setUsername(user.getUsername());
		dto.setUsername(user.getUsername());
		
		return dto;
	}

	@Override
	public BoardDto mapBoardToDto(Board board) {
		BoardDto dto = new BoardDto();
		dto.setId(board.getId());
		dto.setName(board.getName());
		dto.setUserDto(mapUserToDto(board.getUser()));
		return dto;
	}


}
