package com.redbee.challenge.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.redbee.challenge.dto.BoardDto;
import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.service.BoardService;
import com.redbee.challenge.util.QueryResult;
import com.redbee.challenge.util.RestResponse;

/**
 * @author Andres Cassanaz
 *
 */
@RestController
public class BoardController {

	private static Logger LOGGER = LoggerFactory.getLogger(BoardController.class);

	private static int INTERNAL_SERVER_ERRROR = HttpStatus.INTERNAL_SERVER_ERROR.value();
	

	@Autowired
	private BoardService boardService;

	/**
	 * Add new board the board.
	 *
	 * @param boardJson the board in json format
	 * @return a RestResponse
	 */
	@PutMapping("/boards/")
	public RestResponse addBoard(@RequestBody String boardJson) {
		LOGGER.info("PutMapping: " + "/boards/ : "+ boardJson);
		RestResponse restResponse;
		try {
			boardService.save(boardJson);
			restResponse = new RestResponse(HttpStatus.OK.value(), "Ok");
		} catch (JsonParseException e) {
			restResponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: JsonParseException");
			LOGGER.error(restResponse.getMessage());
		} catch (JsonMappingException e) {
			restResponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: JsonMappingException");
			LOGGER.error(restResponse.getMessage());
		} catch (IOException e) {
			restResponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: IOException");
			LOGGER.error(restResponse.getMessage());
		}
		return restResponse;
	}

	/**
	 * Delete board.
	 *
	 * @param boardId the board id
	 * @return a RestResponse
	 */
	@DeleteMapping("/boards/{boardId}")
	public RestResponse deleteBoard(@PathVariable String boardId) {
		LOGGER.info("DeleteMapping: " + "/boards/"+boardId);
		RestResponse restResponse;
		try {
			boardService.delete(boardId);
			restResponse = new RestResponse(HttpStatus.OK.value(), "Ok");
		} catch (JsonParseException e) {
			restResponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: JsonParseException");
			LOGGER.error(restResponse.getMessage());
		} catch (JsonMappingException e) {
			restResponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: JsonMappingException");
			LOGGER.error(restResponse.getMessage());
		} catch (IOException e) {
			restResponse = new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: IOException");
			LOGGER.error(restResponse.getMessage());
		}
		return restResponse;
	}

	/**
	 * Gets complete boards by user.
	 *
	 * @param username the username
	 * @return the boards by user
	 * @throws CityNotFoundException the city not found exception
	 */
	@GetMapping("/boards/{username}")
	public QueryResult getBoardsByUser(@PathVariable String username) throws CityNotFoundException {
		LOGGER.info("GetMapping: " + "/boards/"+username);
		QueryResult queryResult;
		try {
			List<BoardDto> boardsByUser = boardService.getBoardsByUser(username);
			RestResponse restResponse = new RestResponse(HttpStatus.OK.value(), "Ok");
			queryResult = new QueryResult(restResponse, new ArrayList<Object>(boardsByUser));
		} catch (JsonParseException e) {
			queryResult = new QueryResult(new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: JsonParseException"));
			LOGGER.error(queryResult.getRestResponse().getMessage());
		} catch (JsonMappingException e) {
			queryResult = new QueryResult(
					new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: JsonMappingException"));
			LOGGER.error(queryResult.getRestResponse().getMessage());
		} catch (IOException e) {
			queryResult = new QueryResult(new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: IOException"));
			LOGGER.error(queryResult.getRestResponse().getMessage());
		} catch (ParseException e) {
			queryResult = new QueryResult(new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: ParseException"));
			LOGGER.error(queryResult.getRestResponse().getMessage());
		}
		return queryResult;
	}
	

}
