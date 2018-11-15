package com.redbee.challenge.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.User;

@Repository
public interface BoardRepository extends JpaRepository<Board, Serializable> {
	
	List<Board> findByUser(User user);

}
