package com.redbee.challenge.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Board {

	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	@Column
	private String name;

	@ManyToMany(mappedBy = "boards", fetch = FetchType.EAGER)
	private Set<Location> locations = new HashSet<Location>();

	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;

	public Board() {
	}

	public Board(String name, Set<Location> locations) {
		this.name = name;
		this.locations = locations;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Location> getLocations() {
		return locations;
	}

	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
