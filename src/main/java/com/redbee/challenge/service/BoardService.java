package com.redbee.challenge.service;

import java.util.List;
import java.util.Set;

import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.User;
import com.redbee.challenge.util.yahoo.api.data.Condition;

public interface BoardService {

	public void save(Board board);

	public Board findBoardById(long id);

	public Set<Board> findByUser(User user);

	public List<Condition> getActualConditionsByBoard(String boardJson);

}
