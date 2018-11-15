package com.redbee.challenge.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redbee.challenge.model.User;
import com.redbee.challenge.repository.UserRepository;
import com.redbee.challenge.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}


	@Override
	public User findById(long id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			return user.get();
		} else {
			return null;		
		}
	}

}
