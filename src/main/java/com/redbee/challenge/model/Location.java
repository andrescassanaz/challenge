package com.redbee.challenge.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


/**
 * @author Andres Cassanaz
 *
 */
@Entity
public class Location {

	@Id
	@Column
	Long woeid;

	@Column
	String city;

	@Column
	String country;

	// @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@ManyToMany
	@JoinTable(name = "location_x_board", joinColumns = { @JoinColumn(name = "location_id") }, inverseJoinColumns = {
			@JoinColumn(name = "board_id") })
	private Set<Board> boards = new HashSet<Board>();

	@OneToMany(mappedBy = "location", cascade=CascadeType.ALL)
	private List<WeatherPoint> weatherPoints;

	public Location() {
	}

	public Location(long woeid) {
		this.woeid = woeid;
	}

	public Location(long woeid, String city, String country) {
		this.woeid = woeid;
		this.city = city;
		this.country = country;
	}

	public Location(long woeid, String city, String country, Set<Board> boards) {
		this.woeid = woeid;
		this.city = city;
		this.country = country;
		this.boards = boards;
	}

	public Long getWoeid() {
		return woeid;
	}

	public void setWoeid(Long woeid) {
		this.woeid = woeid;
	}

	public Set<Board> getBoards() {
		return boards;
	}

	public void setBoards(Set<Board> boards) {
		this.boards = boards;
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

	public List<WeatherPoint> getWeatherPoints() {
		return weatherPoints;
	}

	public void setWeatherPoints(List<WeatherPoint> weatherPoints) {
		this.weatherPoints = weatherPoints;
	}

}
