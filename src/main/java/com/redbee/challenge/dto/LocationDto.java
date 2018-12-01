package com.redbee.challenge.dto;

import java.util.List;

/**
 * @author Andres Cassanaz
 *
 */
public class LocationDto {

	private Long woeid;
	private String city;
	private String country;
	private Long boardId;
	private List<WeatherPointDto> weatherPoints;

	public Long getWoeid() {
		return woeid;
	}

	public void setWoeid(Long woeid) {
		this.woeid = woeid;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getBoardId() {
		return boardId;
	}

	public void setBoardId(Long boardId) {
		this.boardId = boardId;
	}

	public List<WeatherPointDto> getWeatherPoints() {
		return weatherPoints;
	}

	public void setWeatherPoints(List<WeatherPointDto> weatherPoints) {
		this.weatherPoints = weatherPoints;
	}

}
