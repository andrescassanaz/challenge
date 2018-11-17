package com.redbee.challenge.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redbee.challenge.dto.BoardDto;
import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.service.BoardService;
import com.redbee.challenge.util.RestResponse;
import com.redbee.challenge.util.yahoo.api.YahooRestClientService;
import com.redbee.challenge.util.yahoo.api.data.Condition;
import com.redbee.challenge.util.yahoo.api.data.YahooApiResponse;

@RestController
public class BoardController {

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private BoardService boardService;

	@Autowired
	private YahooRestClientService yahooRestClientService;

	@PostMapping("/board/getactualconditions")
	public List<Condition> delete(@RequestBody String boardJson) {

		List<Condition> conditions = new ArrayList<Condition>();
		try {
			BoardDto BoardDto = this.mapper.readValue(boardJson, BoardDto.class);
			Board board = boardService.findBoardById(BoardDto.getId());
			if (board != null) {
				Set<Location> locations = board.getLocations();
				for (Location location : locations) {
					YahooApiResponse yahooApiResponse = yahooRestClientService
							.getWeatherFromWoeid(location.getWoeid());
					Condition condition = yahooApiResponse.getQuery().getResults().getChannel().getItem()
							.getCondition();
					conditions.add(condition);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return conditions;

	}

}
