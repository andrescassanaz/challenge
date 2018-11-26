package com.redbee.challenge.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {

	@Id
	@Column(unique = true, nullable = false, length = 45)
	private String username;

	@Column
	private String password;

	@OneToMany(mappedBy = "user")
	private List<Board> boards;
	
	private String type;
	
	private String token;
	
	

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	
	

}
