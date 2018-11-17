package com.redbee.challenge.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Location {

	@Id
	@Column
	long woeid;

	@Column
	String city;

	@Column
	String country;

	//@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@ManyToMany
	@JoinTable(name="location_x_board",
				joinColumns={@JoinColumn(name="location_id")},
				inverseJoinColumns= {@JoinColumn(name="board_id")})
	private Set<Board> boards = new HashSet<Board>();

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

	public long getWoeid() {
		return woeid;
	}

	public void setWoeid(long woeid) {
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

}
