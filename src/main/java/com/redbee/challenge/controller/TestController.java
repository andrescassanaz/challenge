package com.redbee.challenge.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.User;
import com.redbee.challenge.service.BoardService;
import com.redbee.challenge.service.LocationService;
import com.redbee.challenge.service.UserService;

@Controller
public class TestController {

	@Autowired
	LocationService locationService;
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/")
	public String test() {
		
		System.out.println("---------- ENTRO A CONTROLLER");
		System.out.println("---------- ENTRO A CONTROLLER");
		System.out.println("---------- ENTRO A CONTROLLER");
		System.out.println("---------- ENTRO A CONTROLLER");
		System.out.println("---------- ENTRO A CONTROLLER");
		System.out.println("---------- ENTRO A CONTROLLER");
		System.out.println("---------- ENTRO A CONTROLLER");
		System.out.println("---------- ENTRO A CONTROLLER");
		
		// CREA DATA
		
		

		User user = new User();
		user.setUsername("Josesito");
		user.setPassword("19923");
		
		userService.save(user);
		
		
		Board board1 = new Board();
		board1.setName("Board 1");
		
		
		

		Location location1 = new Location();
		location1.setWoeid(1111);
		
		Location location2 = new Location();
		location2.setWoeid(2222);
		
		Set<Location> locationsBoard1 = new HashSet<Location>();
		locationsBoard1.add(location1);
		locationsBoard1.add(location2);
		
		board1.setLocations(locationsBoard1);
		
		boardService.save(board1);
		
		//TERMINA CREAR DATA
		
		Board board3 = boardService.findBoardById(1);
		Set<Location> locations = board3.getLocations();
		
		Location location3 = new Location();
		location3.setWoeid(3333);
		
		locations.add(location3);
		
		board3.setLocations(locations);
		User user2 = userService.findById(1);
		board3.setUser(user2);
		boardService.save(board3);
		
		Board board4 = new Board();
		board4.setName("Board4");
		board4.setUser(user2);
		boardService.save(board4);
		
		List<Board> boardsList = boardService.findByUser(user2);
		for (Board board : boardsList) {
			System.out.println("-------- "+ board.getName());
		}
		
		
		
		
		
		return "index";
	}
	
}
