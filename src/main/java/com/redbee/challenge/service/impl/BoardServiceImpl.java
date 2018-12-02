package com.redbee.challenge.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redbee.challenge.dto.BoardDto;
import com.redbee.challenge.dto.LocationDto;
import com.redbee.challenge.dto.WeatherPointDto;
import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.User;
import com.redbee.challenge.repository.BoardRepository;
import com.redbee.challenge.service.BoardService;
import com.redbee.challenge.service.LocationService;
import com.redbee.challenge.service.MapperService;
import com.redbee.challenge.service.UserService;
import com.redbee.challenge.service.WeatherPointService;
import com.redbee.challenge.util.yahoo.api.YahooRestClientService;

/**
 * @author Andres Cassanaz
 *
 */
@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardRepository boardRepository;

	@Autowired
	UserService userService;

	@Autowired
	LocationService locationService;

	@Autowired
	WeatherPointService weatherPointService;

	@Autowired
	YahooRestClientService yahooRestClientService;

	@Autowired
	MapperService mapperService;
	
	private static Logger LOGGER = LoggerFactory.getLogger(BoardServiceImpl.class);

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Board save(String boardJson) throws JsonParseException, JsonMappingException, IOException {
		LOGGER.info("save: " + boardJson);
		BoardDto boardDto = this.mapper.readValue(boardJson, BoardDto.class);
		User user = userService.findByUsername(boardDto.getUserDto().getUsername());

		Board board = new Board(boardDto.getName(), user);
		Board savedBoard = boardRepository.save(board);
		return savedBoard;
	}

	@Override
	public Board findBoardById(long id) {
		LOGGER.info("findBoardById: " + id);
		Optional<Board> board = boardRepository.findById(id);
		return board.get();

	}

	@Override
	public Set<Board> findByUser(User user) {
		LOGGER.info("findByUser: " + user.getUsername());
		return boardRepository.findByUser(user);
	}

	@Override
	public List<BoardDto> getBoardsByUser(String username)
			throws JsonParseException, JsonMappingException, IOException, ParseException, CityNotFoundException {

		LOGGER.info("getBoardsByUser: " + username);
		User user = userService.findByUsername(username);

		Set<Board> boardsOfUser = boardRepository.findByUser(user);

		List<BoardDto> boardsDto = new ArrayList<BoardDto>();
		for (Board board : boardsOfUser) {
			boardsDto.add(mapperService.mapBoardToDto(board));
		}

		return boardsDto;

	}

	@Override
	public List<BoardDto> getBoardsWithLocationsAndWeatherpointsByUser(String username)
			throws JsonParseException, JsonMappingException, IOException, ParseException, CityNotFoundException {
		LOGGER.info("getBoardsWithLocationsAndWeatherpointsByUser: " + username);
		User user = userService.findByUsername(username);
		List<BoardDto> boardsDto = new ArrayList<BoardDto>();
		for (Board board : boardRepository.findByUser(user)) {
			BoardDto boardDto = mapperService.mapBoardToDto(board);
			List<LocationDto> locationsDto = new ArrayList<LocationDto>();

			for (LocationDto locationDto : locationService.getLocationsByBoard(boardDto.getId().toString())) {

				WeatherPointDto lastWeatherPointByLocation = weatherPointService
						.getLastWeatherPointByLocation(locationDto.getWoeid().toString());
				List<WeatherPointDto> weatherPointsDto = new ArrayList<WeatherPointDto>();
				weatherPointsDto.add(lastWeatherPointByLocation);
				locationDto.setWeatherPoints(weatherPointsDto);
				locationsDto.add(locationDto);
			}
			boardDto.setLocations(locationsDto);
			boardsDto.add(boardDto);
		}

		return boardsDto;

	}

	@Override
	public Set<Board> findByLocations(Set<Location> locations) {
		LOGGER.info("findByLocations");
		return boardRepository.findByLocations(locations);
	}

	@Override
	public void delete(String boardId) throws JsonParseException, JsonMappingException, IOException {
		LOGGER.info("delete: " + boardId);
		boardRepository.delete(this.findBoardById(Long.parseLong(boardId)));
	}

}
