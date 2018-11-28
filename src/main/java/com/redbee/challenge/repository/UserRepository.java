package com.redbee.challenge.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redbee.challenge.model.User;

/**
 * @author Andres Cassanaz
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Serializable>{

	/**
	 * Find user by username.
	 *
	 * @param username the username
	 * @return the user
	 */
	public User findByUsername(String username);
	
	
}
