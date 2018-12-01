package com.redbee.challenge.dto;

import java.util.List;

/**
 * @author Andres Cassanaz
 *
 */
public class BoardDto {

	private Long id;
	private String name;
	private List<LocationDto> locations;
	private UserDto userDto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LocationDto> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationDto> locations) {
		this.locations = locations;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

}