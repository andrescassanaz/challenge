package com.redbee.challenge.service;

public interface ApiCounterService {
	
	
	/**
	 * Update the date of api counter
	 */
	void updateDate();
	
	
	/**
	 * Add a call to the api
	 */
	void AddCall();
	
	/**
	 * Check if the limit of daily calls to the api is exceeded
	 * @return
	 */
	boolean callLimitExceeded();

	/**
	 * Check if the limit of daily calls to the api is exceeded taking into account the date
	 * @return
	 */
	boolean isCallLimitExceededPerDay();

}
