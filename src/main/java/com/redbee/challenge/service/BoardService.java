package com.redbee.challenge.service;

import java.util.List;
import java.util.Set;

import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.User;
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

}
