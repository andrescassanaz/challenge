package com.redbee.challenge.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redbee.challenge.dto.BoardDto;
import com.redbee.challenge.dto.LocationDto;
import com.redbee.challenge.dto.UserDto;
import com.redbee.challenge.exception.BoardNotFoundException;
import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.exception.LocationNotFoundException;
import com.redbee.challenge.model.Board;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.User;
import com.redbee.challenge.model.WeatherPoint;
import com.redbee.challenge.repository.BoardRepository;
import com.redbee.challenge.repository.LocationRepository;
import com.redbee.challenge.service.BoardService;
import com.redbee.challenge.service.LocationService;
import com.redbee.challenge.service.MapperService;
import com.redbee.challenge.service.UserService;
import com.redbee.challenge.service.WeatherPointService;
import com.redbee.challenge.util.yahoo.api.YahooRestClientService;
import com.redbee.challenge.util.yahoo.api.data.Condition;
import com.redbee.challenge.util.yahoo.api.data.YahooApiResponse;

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

	private ObjectMapper mapper = new ObjectMapper();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.redbee.challenge.service.BoardService#save(com.redbee.challenge.model.
	 * Board)
	 */
	@Override
	public Board save(String boardJson) throws JsonParseException, JsonMappingException, IOException {

		BoardDto boardDto = this.mapper.readValue(boardJson, BoardDto.class);
		User user = userService.findById(boardDto.getUserDto().getId());

		Board board = new Board(boardDto.getName(), user);
		Board savedBoard = boardRepository.save(board);
		return savedBoard;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.redbee.challenge.service.BoardService#findBoardById(long)
	 */
	@Override
	public Board findBoardById(long id) {
		Optional<Board> board = boardRepository.findById(id);
		return board.get();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.redbee.challenge.service.BoardService#findByUser(com.redbee.challenge.
	 * model.User)
	 */
	@Override
	public Set<Board> findByUser(User user) {
		return boardRepository.findByUser(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.redbee.challenge.service.BoardService#getActualWeatherByBoard(java.lang.
	 * String)
	 */
	@Override
	public List<Condition> getActualWeatherByBoard(String boardJson) throws CityNotFoundException {
		List<Condition> conditions = new ArrayList<Condition>();
		try {
			BoardDto BoardDto = this.mapper.readValue(boardJson, BoardDto.class);
			Board board = this.findBoardById(BoardDto.getId());
			if (board != null) {
				Set<Location> locations = board.getLocations();
				for (Location location : locations) {
					YahooApiResponse yahooApiResponse = yahooRestClientService.getWeatherFromWoeid(location.getWoeid());
					Condition condition = yahooApiResponse.getQuery().getResults().getChannel().getItem()
							.getCondition();
					conditions.add(condition);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return conditions;
	}

	@Override
	public List<BoardDto> getBoardsByUser(String userJson)
			throws JsonParseException, JsonMappingException, IOException, ParseException, CityNotFoundException {

		UserDto userDto = this.mapper.readValue(userJson, UserDto.class);

		User user = userService.findById(userDto.getId());

		Set<Board> boardsOfUser = boardRepository.findByUser(user);

		List<BoardDto> boardsDto = new ArrayList<BoardDto>();
		for (Board board : boardsOfUser) {
			boardsDto.add(mapperService.mapBoardToDto(board));
			for (Location location : board.getLocations()) {
				YahooApiResponse weatherResponse = yahooRestClientService.getWeatherFromWoeid(location.getWoeid());
				WeatherPoint weatherPoint = weatherPointService.buildWeatherPoint(location, weatherResponse);
				weatherPointService.saveIfNecessary(weatherPoint);
				List<WeatherPoint> weatherPoints = new ArrayList<WeatherPoint>();
				weatherPoints.add(weatherPoint);
				location.setWeatherPoints(weatherPoints);
			}
		}

		return boardsDto;

	}

	@Override
	public Set<Board> findByLocations(Set<Location> locations) {
		return boardRepository.findByLocations(locations);
	}

	@Override
	public void delete(String boardJson) throws JsonParseException, JsonMappingException, IOException {
		BoardDto boardDto = this.mapper.readValue(boardJson, BoardDto.class);
		boardRepository.delete(this.findBoardById(boardDto.getId()));

	}

	@Override
	public List<BoardDto> getBoardsAndLocationsByUser(String userJson)
			throws JsonParseException, JsonMappingException, IOException {

		UserDto userDto = this.mapper.readValue(userJson, UserDto.class);

		User user = userService.findById(userDto.getId());

		Set<Board> boardsOfUser = boardRepository.findByUser(user);

		List<BoardDto> boardsDto = new ArrayList<BoardDto>();
		for (Board board : boardsOfUser) {
			boardsDto.add(mapperService.mapBoardToDto(board));
		}

		return boardsDto;

	}

	

}
