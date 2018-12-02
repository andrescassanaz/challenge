package com.redbee.challenge.service;

/**
 * @author Andres Cassanaz
 *
 */
public interface WebSocketService {

	/**
	 * Start the scheduled task in the websocket
	 * 
	 * @param username
	 */
	public void startWebsocketScheduler(String username);

	/**
	 * stop the scheduled task in the websocket
	 */
	public void stopWebsocketScheduler();

}
