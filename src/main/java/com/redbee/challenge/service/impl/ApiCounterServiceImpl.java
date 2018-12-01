package com.redbee.challenge.service.impl;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redbee.challenge.exception.YahooApiCallLimitExceededException;
import com.redbee.challenge.model.ApiCounter;
import com.redbee.challenge.repository.ApiCounterRepository;
import com.redbee.challenge.service.ApiCounterService;

@Service
public class ApiCounterServiceImpl implements ApiCounterService {

	@Autowired
	ApiCounterRepository apiCounterRepository;

	@Override
	public void updateDate() {
		apiCounterRepository.save(getNewApiCounter());
	}

	@Override
	public void AddCall() {
		ApiCounter apiCounter = getTheApiCounter();
		Integer currentCalls = apiCounter.getApiCalls();
		apiCounter.setApiCalls(currentCalls + 1);
		apiCounterRepository.save(apiCounter);

	}

	@Override
	public boolean callLimitExceeded() {
		boolean callLimitExceeded = false;
		ApiCounter apiCounter = getTheApiCounter();
		if (apiCounter.getApiCalls() >= 2000) {
			callLimitExceeded = true;
		}
		return callLimitExceeded;
	}

	@Override
	public boolean isCallLimitExceededPerDay() {
		boolean isCallLimitExceededPerDay = false;
		ApiCounter apiCounter = getTheApiCounter();
		long actualDateInMillis = getRoundedDateInMillis(System.currentTimeMillis());
		if (actualDateInMillis == apiCounter.getDate()) {
			if (!callLimitExceeded()) {
				AddCall();
			} else {
				isCallLimitExceededPerDay = true;
			}
		} else if (actualDateInMillis > apiCounter.getDate()) {
			updateDate();
			isCallLimitExceededPerDay();
		}
		return isCallLimitExceededPerDay;
	}

	private ApiCounter getTheApiCounter() {
		ApiCounter apiCounter;
		Optional<ApiCounter> optionalApiCounter = apiCounterRepository.findById(0);
		if (optionalApiCounter.isPresent()) {
			apiCounter = optionalApiCounter.get();
		} else {
			apiCounter = getNewApiCounter();
		}

		return apiCounter;
	}

	private ApiCounter getNewApiCounter() {
		ApiCounter apiCounter = new ApiCounter();
		apiCounter.setId(0);
		apiCounter.setDate(getRoundedDateInMillis(System.currentTimeMillis()));
		apiCounter.setApiCalls(0);
		return apiCounter;
	}

	private Long getRoundedDateInMillis(long dateInMillis) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(dateInMillis);
		date.set(Calendar.MILLISECOND, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.HOUR_OF_DAY, 0);
		return date.getTimeInMillis();
	}

}
