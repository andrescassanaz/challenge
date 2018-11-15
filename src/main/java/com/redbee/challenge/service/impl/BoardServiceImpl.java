package com.redbee.challenge.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.User;
import com.redbee.challenge.repository.BoardRepository;
import com.redbee.challenge.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardRepository boardRepository;

	@Override
	public void save(Board board) {
		boardRepository.save(board);

	}

	@Override
	public Board findBoardById(long id) {
		Optional<Board> board = boardRepository.findById(id);
		if(board.isPresent()){
			return board.get();
		} else {
			return null;
		}

	}

	@Override
	public List<Board> findByUser(User user) {
		return boardRepository.findByUser(user);
	}

}
