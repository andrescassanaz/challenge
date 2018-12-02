package com.redbee.challenge.controller;

import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.redbee.challenge.service.WebSocketService;

@RestController
public class WebSocketController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(WebSocketController.class);

	@Autowired
	WebSocketService webSocketService;
	ScheduledExecutorService currentExecutor;

	/**
	 * Start the scheduled task in the websocket
	 * @param username
	 */
	@PostMapping("/startScheduler")
	public void startScheduler(@RequestBody String username) {
		LOGGER.info("PostMapping: /startScheduler");
		webSocketService.startWebsocketScheduler(username);
	}

	/**
	 * Stop the scheduled task in the websocket
	 */
	@PostMapping("/stopScheduler")
	public void StopSchedule() {
		LOGGER.info("PostMapping: /stopScheduler");
		webSocketService.stopWebsocketScheduler();

	}
	
	

}
