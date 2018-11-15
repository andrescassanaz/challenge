package com.redbee.challenge.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redbee.challenge.dto.LocationDto;
import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.service.BoardService;
import com.redbee.challenge.service.LocationService;
import com.redbee.challenge.util.RestResponse;

@RestController
public class LocationController {

	@Autowired
	private LocationService locationService;
	
	@Autowired
	private BoardService boardService;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@PostMapping("/location/save")
	public RestResponse saveOrUpdate(@RequestBody String locationJson) {
		RestResponse restResponse;
		try {
			LocationDto locationDto = this.mapper.readValue(locationJson, LocationDto.class);
			Location location = new Location(locationDto.getWoeid());
			
			Board board = boardService.findBoardById(locationDto.getBoardId());
			
			Set<Board> boards = new HashSet<Board>();
			boards.add(board);
			location.setBoards(boards);
			
			locationService.save(location);
			
			restResponse = new RestResponse(HttpStatus.OK.value(),"Location saved!");
		} catch (IOException e) {
			e.printStackTrace();
			restResponse = new RestResponse(HttpStatus.CONFLICT.value(),"Error mapping location");
		}
		return restResponse;		
		
	}
	
	@PostMapping("/location/delete")
	public RestResponse delete(@RequestBody String locationJson) {
		RestResponse restResponse;
		try {
			LocationDto locationDto = this.mapper.readValue(locationJson, LocationDto.class);
			Location location = locationService.findById(locationDto.getWoeid());
			if(location!= null) {
				locationService.delete(location);
				restResponse = new RestResponse(HttpStatus.OK.value(),"Location Deleted!");
			} else {
				restResponse = new RestResponse(HttpStatus.CONFLICT.value(),"Nonexistent location!");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			restResponse = new RestResponse(HttpStatus.CONFLICT.value(),"Error mapping location");
		}
		return restResponse;		
		
	}
	
	
}
