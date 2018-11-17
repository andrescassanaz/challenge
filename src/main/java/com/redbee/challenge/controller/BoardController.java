package com.redbee.challenge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.redbee.challenge.service.BoardService;
import com.redbee.challenge.util.yahoo.api.data.Condition;

@RestController
public class BoardController {

	@Autowired
	private BoardService boardService;

	/**
	 * Gets the actual conditions by board.
	 *
	 * @param boardJson the board json
	 * @return the actual conditions by board
	 */
	@PostMapping("/getActualConditionsByBoard")
	public List<Condition> getActualConditionsByBoard(@RequestBody String boardJson) {
		List<Condition> conditions = boardService.getActualWeatherByBoard(boardJson);
		return conditions;
	}

}
