package com.redbee.challenge.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.redbee.challenge.dto.BoardDto;
import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.User;
import com.redbee.challenge.model.WeatherPoint;
import com.redbee.challenge.util.yahoo.api.data.Condition;

public interface BoardService {

	/**
	 * Save.
	 *
	 * @param board the board
	 */
	public void save(Board board);

	/**
	 * Find board by id.
	 *
	 * @param id the id
	 * @return the board
	 */
	public Board findBoardById(long id);

	/**
	 * Find board by user.
	 *
	 * @param user the user
	 * @return the sets the
	 */
	public Set<Board> findByUser(User user);

	/**
	 * Gets the actual weather by board.
	 *
	 * @param boardJson the board json
	 * @return the actual weather by board
	 */
	public List<Condition> getActualWeatherByBoard(String boardJson);




	public List<BoardDto> getBoardsByUser(String userJson) throws JsonParseException, JsonMappingException, IOException, ParseException;

}
