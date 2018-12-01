package com.redbee.challenge.service;

/**
 * @author Andres Cassanaz
 *
 */
public interface WebSocketService {

	public void startWebsocketScheduler(String username);

	public void stopWebsocketScheduler();

}
