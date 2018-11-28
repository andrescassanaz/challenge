package com.redbee.challenge.service;

import com.redbee.challenge.model.User;

/**
 * @author Andres Cassanaz
 *
 */
public interface UserService {
	
	/**
	 * Save.
	 *
	 * @param user the user
	 * @return the saved user
	 */
	public User save(User user);
	

	/**
	 * Find by username.
	 *
	 * @param username the username
	 * @return the user
	 */
	public User findByUsername(String username);

}
