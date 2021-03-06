package com.redbee.challenge.repository;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Serializable>{

	/**
	 * Find location by boards.
	 *
	 * @param boards the boards
	 * @return the location by boards
	 */
	Set<Location> findByBoards(Set<Board> boards);
	

}
