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
import com.redbee.challenge.controller.WebSocketController;
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
	public User login(User user) {
		LOGGER.info("login(): " + user.getUsername());
		final Authentication authentication;

		authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		String tokenJwt = jwtService.generateToken(user.getUsername());
		
		User dbUser = userService.findByUsername(user.getUsername());
		user.setType(dbUser.getType());
		user.setPassword(null);
		user.setToken(tokenJwt);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		return user;

	}
}
