package com.redbee.challenge.service;

import com.redbee.challenge.model.User;

public interface UserService {
	
	/**
	 * Save.
	 *
	 * @param user the user
	 * @return the saved user
	 */
	public User save(User user);
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the saved user
	 */
	public User findById(long id);

}
