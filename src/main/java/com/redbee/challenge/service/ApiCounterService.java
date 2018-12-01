package com.redbee.challenge.service;

public interface ApiCounterService {
	
	void updateDate();
	
	void AddCall();
	
	boolean callLimitExceeded();

	boolean isCallLimitExceededPerDay();

}
