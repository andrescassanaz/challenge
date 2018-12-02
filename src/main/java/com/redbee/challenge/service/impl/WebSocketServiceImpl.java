package com.redbee.challenge.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.redbee.challenge.dto.BoardDto;
import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.service.BoardService;
import com.redbee.challenge.service.WebSocketService;

/**
 * @author Andres Cassanaz
 *
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {

	private static Logger LOGGER = LoggerFactory.getLogger(WebSocketServiceImpl.class);
	private final SimpMessagingTemplate template;
	
	@Value("${web.socket.scheduled.task.interval}")
	private int webSocketScheduledTaskInterval;

	@Autowired
	BoardService boardService;

	@Autowired
	WebSocketServiceImpl(SimpMessagingTemplate template) {
		this.template = template;
	}

	ScheduledExecutorService currentExecutor;
	String username = "";

	@Override
	public void startWebsocketScheduler(String username) {
		LOGGER.info("Start Websocket Scheduler");
		this.username = username;
		currentExecutor = Executors.newScheduledThreadPool(1);
		currentExecutor.scheduleAtFixedRate(startChecker, 0, webSocketScheduledTaskInterval, TimeUnit.MINUTES);
	}

	@Override
	public void stopWebsocketScheduler() {
		if (currentExecutor != null) {
			LOGGER.info("Stop Websocket Scheduler");
			currentExecutor.shutdownNow();
		}
	}

	public void onReceivedMesage(List<BoardDto> boardsDto) {
		LOGGER.info("Backend sends weather updates to the websocket channel");
		this.template.convertAndSend("/channel", boardsDto);
	}

	Runnable startChecker = new Runnable() {
		public void run() {
			LOGGER.info("Start WebSocket scheduled task");
			List<BoardDto> boardsDto;
			try {
				boardsDto = boardService.getBoardsWithLocationsAndWeatherpointsByUser(username);
				onReceivedMesage(boardsDto);
			} catch (IOException | ParseException | CityNotFoundException e) {
				LOGGER.error("Error updating the weather through websocket");
			}

		}
	};

}
