package com.redbee.challenge.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.redbee.challenge.dto.BoardDto;
import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.User;
import com.redbee.challenge.util.yahoo.api.data.Condition;

public interface BoardService {

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

	public List<BoardDto> getBoardsByUser(String userJson)
			throws JsonParseException, JsonMappingException, IOException, ParseException;

	public Board save(String boardJson) throws JsonParseException, JsonMappingException, IOException;

	public Set<Board> findByLocations(Set<Location> locations);

	public void delete(String boardJson) throws JsonParseException, JsonMappingException, IOException;

}
