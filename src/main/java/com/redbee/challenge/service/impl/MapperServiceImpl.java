package com.redbee.challenge.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.redbee.challenge.dto.BoardDto;
import com.redbee.challenge.dto.LocationDto;
import com.redbee.challenge.dto.UserDto;
import com.redbee.challenge.dto.UserRoleDto;
import com.redbee.challenge.dto.WeatherPointDto;
import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.User;
import com.redbee.challenge.model.UserRole;
import com.redbee.challenge.model.WeatherPoint;
import com.redbee.challenge.service.MapperService;

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
		dto.setToken(user.getToken());
		dto.setType(user.getType());
		List<UserRoleDto> userRoles = new ArrayList<UserRoleDto>();
		for (UserRole userRole : user.getUserRole()) {
			userRoles.add(mapUserRoleToDto(userRole));
		}
		dto.setUserRol(userRoles);
		
		
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

	@Override
	public UserRoleDto mapUserRoleToDto(UserRole userRole) {
		return new UserRoleDto(userRole.getId(), userRole.getRole());
	}

}
