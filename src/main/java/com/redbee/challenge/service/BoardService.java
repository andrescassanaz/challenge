package com.redbee.challenge.service;

import java.util.List;

import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.User;

public interface BoardService {

	public void save(Board board);

	public Board findBoardById(long id);

	public List<Board> findByUser(User user);

}
