/*
 * 
 */
package com.redbee.challenge.repository;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.User;

@Repository
public interface BoardRepository extends JpaRepository<Board, Serializable> {

	/**
	 * Find by user.
	 *
	 * @param user the user
	 * @return Boards of a user
	 */
	Set<Board> findByUser(User user);

	Set<Board> findByLocations(Set<Location> location);

}
