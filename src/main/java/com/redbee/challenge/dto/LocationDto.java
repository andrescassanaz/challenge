package com.redbee.challenge.dto;

import java.util.List;

/**
 * @author Andres Cassanaz
 *
 */
public class LocationDto {

	private long woeid;
	private String city;
	private String country;
	private long boardId;
	private List<WeatherPointDto> weatherPoints;

	public long getBoardId() {
		return boardId;
	}

	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public long getWoeid() {
		return woeid;
	}

	public void setWoeid(long woeid) {
		this.woeid = woeid;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<WeatherPointDto> getWeatherPoints() {
		return weatherPoints;
	}

	public void setWeatherPoints(List<WeatherPointDto> weatherPoints) {
		this.weatherPoints = weatherPoints;
	}

}
