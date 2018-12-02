package com.redbee.challenge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.redbee.challenge.configuration.JwtService;
import com.redbee.challenge.dto.UserDto;
import com.redbee.challenge.model.User;
import com.redbee.challenge.service.UserService;

/**
 * @author Andres Cassanaz
 *
 */
@Service
public class AuthenticationService implements com.redbee.challenge.service.AuthenticationService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@Override
	public UserDto login(User user) {
		LOGGER.info("login(): " + user.getUsername());
		final Authentication authentication;

		authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		String tokenJwt = jwtService.generateToken(user.getUsername());
		
		UserDto userDto = new UserDto();
		userDto.setUsername(user.getUsername());
		userDto.setPassword(null);
		userDto.setToken(tokenJwt);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		return userDto;

	}
}
