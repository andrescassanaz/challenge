package com.redbee.challenge.service;

import com.redbee.challenge.model.User;

public interface UserService {
	
	public User save(User user);
	
	public User findById(long id);

}
