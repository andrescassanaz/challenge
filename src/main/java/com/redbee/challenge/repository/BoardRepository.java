package com.redbee.challenge.repository;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.User;

/**
 * @author Andres Cassanaz
 *
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Serializable> {

	/**
	 * Find boards by user.
	 *
	 * @param user the user
	 * @return the set of boards
	 */
	Set<Board> findByUser(User user);

	/**
	 * Find boards by locations.
	 *
	 * @param location the location
	 * @return the sets of boards
	 */
	Set<Board> findByLocations(Set<Location> location);

}
