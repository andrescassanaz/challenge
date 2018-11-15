package com.redbee.challenge.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Location {

	@Id
	@Column
	long woeid;

	@ManyToMany(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Set<Board> boards = new HashSet<Board>();

	public Location() {
	}

	public Location(long woeid) {
		this.woeid = woeid;
	}

//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}

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

}
