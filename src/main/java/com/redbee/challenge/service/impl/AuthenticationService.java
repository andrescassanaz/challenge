package com.redbee.challenge.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.redbee.challenge.configuration.JwtService;
import com.redbee.challenge.model.User;
import com.redbee.challenge.service.UserService;

@Service
public class AuthenticationService implements com.redbee.challenge.service.AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@Override
	public User login(User user) {
		final Authentication authentication;

		authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		User bdUser = userService.findByUsername(user.getUsername());
		user.setUserRole(bdUser.getUserRole());
		user.setPassword(null);

		String jwt = jwtService.generateToken(user.getUsername());
		user.setToken(jwt);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return user;

	}
}
