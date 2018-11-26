package com.redbee.challenge.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.redbee.challenge.model.User;
import com.redbee.challenge.service.UserService;
import com.redbee.challenge.util.QueryResult;
import com.redbee.challenge.util.RestResponse;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/users/{username}/{password}")
	public QueryResult getUser(@PathVariable String username, @PathVariable String password) {
		QueryResult queryResult;
		RestResponse restResponse = new RestResponse(HttpStatus.OK.value(), "Ok");
		User user = userService.findByUsername(username);
		List<User> users = new ArrayList<User>();
		users.add(user);
		queryResult = new QueryResult(restResponse, new ArrayList<Object>(users));

		return queryResult;
	}

}
