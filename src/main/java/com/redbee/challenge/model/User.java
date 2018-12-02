package com.redbee.challenge.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author Andres Cassanaz
 *
 */
@Entity
public class User {

	@Id
	@Column(unique = true, nullable = false, length = 45)
	private String username;

	@Column
	private String password;

	@OneToMany(mappedBy = "user")
	private List<Board> boards;
	

	public User() {	}
	

	public User(String username, String password, boolean enabled) {
		this.username = username;
		this.password = password;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
