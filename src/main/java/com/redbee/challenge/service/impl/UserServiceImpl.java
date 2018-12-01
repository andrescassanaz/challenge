package com.redbee.challenge.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.redbee.challenge.model.User;
import com.redbee.challenge.repository.UserRepository;
import com.redbee.challenge.service.UserService;

@Service
@Configuration
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	private static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User save(User user) {
		LOGGER.info("save: "+user.getUsername());
		return userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		LOGGER.info("findByUsername: "+username);
		return userRepository.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.info("loadUserByUsername: "+username);
		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

		grantedAuthorities.add(new SimpleGrantedAuthority("USER"));

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				grantedAuthorities);

	}

}
