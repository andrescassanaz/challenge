package com.redbee.challenge.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.redbee.challenge.dto.UserDto;
import com.redbee.challenge.model.User;
import com.redbee.challenge.service.AuthenticationService;
import com.redbee.challenge.service.MapperService;
import com.redbee.challenge.util.QueryResult;
import com.redbee.challenge.util.RestResponse;

/**
 * The Class AuthenticatioController.
 *
 * @author Andres Cassanaz
 */
@RestController
public class AuthenticatioController {
	
	public AuthenticatioController() {}

	@Autowired
	AuthenticationService autenticationService;
	
	@Autowired
	MapperService mapperService;
	
	private static Logger LOGGER = LoggerFactory.getLogger(AuthenticatioController.class);

	/**
	 * Login.
	 *
	 * @param user The user
	 * @return a QueryResult
	 */
	@PostMapping("/login")
	public QueryResult login(@RequestBody User user) {
		LOGGER.info("PostMapping: " + "/login");
		UserDto userDto = autenticationService.login(user);
		
		List<UserDto> users = new ArrayList<UserDto>();
		users.add(userDto);
		RestResponse restResponse = new RestResponse(HttpStatus.OK.value(), "Ok");
		return new QueryResult(restResponse, new ArrayList<Object>(users));

	}

}
