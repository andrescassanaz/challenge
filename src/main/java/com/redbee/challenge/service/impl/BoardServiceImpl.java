package com.redbee.challenge.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redbee.challenge.dto.BoardDto;
import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.User;
import com.redbee.challenge.repository.BoardRepository;
import com.redbee.challenge.service.BoardService;
import com.redbee.challenge.util.yahoo.api.YahooRestClientService;
import com.redbee.challenge.util.yahoo.api.data.Condition;
import com.redbee.challenge.util.yahoo.api.data.YahooApiResponse;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardRepository boardRepository;

	@Autowired
	YahooRestClientService yahooRestClientService;

	@Override
	public void save(Board board) {
		boardRepository.save(board);

	}

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public Board findBoardById(long id) {
		Optional<Board> board = boardRepository.findById(id);
		if (board.isPresent()) {
			return board.get();
		} else {
			return null;
		}

	}

	@Override
	public Set<Board> findByUser(User user) {
		return boardRepository.findByUser(user);
	}

	@Override
	public List<Condition> getActualConditionsByBoard(String boardJson) {
		List<Condition> conditions = new ArrayList<Condition>();
		try {
			BoardDto BoardDto = this.mapper.readValue(boardJson, BoardDto.class);
			Board board = this.findBoardById(BoardDto.getId());
			if (board != null) {
				Set<Location> locations = board.getLocations();
				for (Location location : locations) {
					YahooApiResponse yahooApiResponse = yahooRestClientService.getWeatherFromWoeid(location.getWoeid());
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
