package com.redbee.challenge.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.redbee.challenge.dto.UserDto;
import com.redbee.challenge.model.User;
import com.redbee.challenge.service.AuthenticationService;
import com.redbee.challenge.service.MapperService;
import com.redbee.challenge.util.QueryResult;
import com.redbee.challenge.util.RestResponse;

@RestController
@CrossOrigin
public class AuthenticationController {

	@Autowired
	AuthenticationService autenticationService;
	
	@Autowired
	MapperService mapperService;

	@PostMapping("/login")
	public QueryResult login(@RequestBody User user) {

		User userAuth = autenticationService.login(user);
		UserDto userDto = mapperService.mapUserToDto(userAuth);
		
		
		List<UserDto> users = new ArrayList<UserDto>();
		users.add(userDto);
		RestResponse restResponse = new RestResponse(HttpStatus.OK.value(), "Ok");
		return new QueryResult(restResponse, new ArrayList<Object>(users));

	}

}
