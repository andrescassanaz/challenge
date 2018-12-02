package com.redbee.challenge.service;

import com.redbee.challenge.dto.UserDto;
import com.redbee.challenge.model.User;

/**
 * @author Andres Cassanaz
 *
 */
public interface AuthenticationService {

	/**
	 * User login.
	 *
	 * @param user the user
	 * @return the user
	 */
	UserDto login(User user);

}
