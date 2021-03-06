package com.redbee.challenge.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.redbee.challenge.model.User;
import com.redbee.challenge.service.UserService;
import com.redbee.challenge.util.QueryResult;
import com.redbee.challenge.util.RestResponse;

/**
 * @author Andres Cassanaz
 *
 */
@RestController
public class UserController {

	@Autowired
	UserService userService;

	private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	

	/**
	 * Gets the user.
	 *
	 * @param username the username
	 * @param password the password
	 * @return the user
	 */
	@GetMapping("/users/{username}/{password}")
	public QueryResult getUser(@PathVariable String username, @PathVariable String password) {
		LOGGER.info("GetMapping: " + "users/" + username + "/" + password);
		QueryResult queryResult;
		RestResponse restResponse = new RestResponse(HttpStatus.OK.value(), "Ok");
		User user = userService.findByUsername(username);
		List<User> users = new ArrayList<User>();
		users.add(user);
		queryResult = new QueryResult(restResponse, new ArrayList<Object>(users));

		return queryResult;
	}


}
