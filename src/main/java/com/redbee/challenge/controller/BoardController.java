package com.redbee.challenge.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.redbee.challenge.dto.BoardDto;
import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.service.BoardService;
import com.redbee.challenge.util.QueryResult;
import com.redbee.challenge.util.RestResponse;
import com.redbee.challenge.util.yahoo.api.data.Condition;

@RestController
@CrossOrigin
public class BoardController {

	private static Logger LOGGER = LoggerFactory.getLogger(BoardController.class);

	private static int INTERNAL_SERVER_ERRROR = HttpStatus.INTERNAL_SERVER_ERROR.value();

	@Autowired
	private BoardService boardService;

	@PostMapping("/addBoard")
	public RestResponse addBoard(@RequestBody String boardJson) {
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

	@PostMapping("/deleteBoard")
	public RestResponse deleteBoard(@RequestBody String boardJson) {
		RestResponse restResponse;
		try {
			boardService.delete(boardJson);
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
	 * @param userJson the user json
	 * @return the boards by user
	 * @throws CityNotFoundException
	 */
	@PostMapping("/getBoardsByUserWithUpdatedWeather")
	public QueryResult getBoardsByUser(@RequestBody String userJson) throws CityNotFoundException {
		LOGGER.info("Executing getBoardsByUserWithUpdatedWeather()");
		QueryResult queryResult;
		try {
			List<BoardDto> boardsByUser = boardService.getBoardsByUser(userJson);
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

	@PostMapping("/getBoardsByUser")
	public QueryResult getBoardsAndLocationsByUser(@RequestBody String userJson) {
		QueryResult queryResult;
		try {
			List<BoardDto> boardsByUser = boardService.getBoardsAndLocationsByUser(userJson);
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
		}
		return queryResult;
	}

	/**
	 * Gets the actual weather by board.
	 *
	 * @param boardJson the board json
	 * @return the actual weather by board
	 * @throws CityNotFoundException
	 */
	@PostMapping("/getActualWeatherByBoard")
	public List<Condition> getActualWeatherByBoard(@RequestBody String boardJson) throws CityNotFoundException {
		List<Condition> conditions = boardService.getActualWeatherByBoard(boardJson);
		return conditions;
	}

}
