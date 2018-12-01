package com.redbee.challenge.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.redbee.challenge.dto.BoardDto;
import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.User;

/**
 * @author Andres Cassanaz
 *
 */
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
	 * @return the sets of boards
	 */
	public Set<Board> findByUser(User user);

	/**
	 * Gets the actual weather by board.
	 *
	 * @param username the username
	 * @return the actual weather by board
	 * @throws JsonParseException json parse exception
	 * @throws JsonMappingException json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException parse exception
	 * @throws CityNotFoundException city not found exception
	 */

	public List<BoardDto> getBoardsByUser(String username)
			throws JsonParseException, JsonMappingException, IOException, ParseException, CityNotFoundException;

	/**
	 * Save or update a board.
	 *
	 * @param boardJson the board json
	 * @return the saved board
	 * @throws JsonParseException the json parse exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Board save(String boardJson) throws JsonParseException, JsonMappingException, IOException;

	/**
	 * Find boards by locations.
	 *
	 * @param locations the locations
	 * @return the sets of boards
	 */
	public Set<Board> findByLocations(Set<Location> locations);

	/**
	 * Delete.
	 *
	 * @param boardId the board id
	 * @throws JsonParseException the json parse exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void delete(String boardId) throws JsonParseException, JsonMappingException, IOException;

	List<BoardDto> getBoardsWithLocationsAndWeatherpointsByUser(String userJson)
			throws JsonParseException, JsonMappingException, IOException, ParseException, CityNotFoundException;


}
