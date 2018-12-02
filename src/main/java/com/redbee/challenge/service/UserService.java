package com.redbee.challenge.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.redbee.challenge.model.User;

/**
 * @author Andres Cassanaz
 *
 */
public interface UserService {
	
	/**
	 * Save.
	 *
	 * @param userJson the user
	 * @return the saved user
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public User save(String userJson) throws JsonParseException, JsonMappingException, IOException;
	

	/**
	 * Find by username.
	 *
	 * @param username the username
	 * @return the user
	 */
	public User findByUsername(String username);

}
