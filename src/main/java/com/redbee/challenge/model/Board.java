package com.redbee.challenge.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;

/**
 * @author Andres Cassanaz
 *
 */
@Entity
public class Board {

	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column
	private String name;

	@ManyToMany(mappedBy = "boards", fetch = FetchType.EAGER)
	private Set<Location> locations = new HashSet<Location>();

	@PreRemove
	private void removeBoardFromLocation() {
		for (Location location : locations) {
			location.getBoards().remove(this);
		}
	}

	@ManyToOne
	@JoinColumn(name = "username")
	private User user;

	public Board() {
	}

	public Board(String name, Set<Location> locations) {
		this.name = name;
		this.locations = locations;
	}

	public Board(String name, User user) {
		this.name = name;
		this.user = user;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
