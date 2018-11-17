package com.redbee.challenge.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.redbee.challenge.dto.BoardDto;
import com.redbee.challenge.service.BoardService;
import com.redbee.challenge.util.QueryResult;
import com.redbee.challenge.util.RestResponse;
import com.redbee.challenge.util.yahoo.api.data.Condition;

@RestController
public class BoardController {

	private static int INTERNAL_SERVER_ERRROR = HttpStatus.INTERNAL_SERVER_ERROR.value();

	@Autowired
	private BoardService boardService;

	/**
	 * Gets the actual weather by board.
	 *
	 * @param boardJson the board json
	 * @return the actual weather by board
	 */
	@PostMapping("/getActualWeatherByBoard")
	public List<Condition> getActualWeatherByBoard(@RequestBody String boardJson) {
		List<Condition> conditions = boardService.getActualWeatherByBoard(boardJson);
		return conditions;
	}

	@GetMapping("/getBoardsByUser")
	public QueryResult getBoardsByUser(@RequestBody String userJson) {

		try {
			List<BoardDto> boardsByUser = boardService.getBoardsByUser(userJson);
			RestResponse restResponse = new RestResponse(HttpStatus.OK.value(), "Error mapping location");
			return new QueryResult(restResponse, new ArrayList<Object>(boardsByUser));
		} catch (JsonParseException e) {
			return new QueryResult(new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: JsonParseException"));
		} catch (JsonMappingException e) {
			return new QueryResult(new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: JsonMappingException"));
		} catch (IOException e) {
			return new QueryResult(new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: IOException"));
		} catch (ParseException e) {
			return new QueryResult(new RestResponse(INTERNAL_SERVER_ERRROR, "Server error: ParseException"));
		}
	}

}
