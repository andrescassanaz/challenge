package com.redbee.challenge.util;

import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.service.WeatherCheckerService;

@Service
public class WeatherChecker {

	private static Logger LOGGER = LoggerFactory.getLogger(WeatherChecker.class);

	@Autowired
	WeatherCheckerService weatherCheckerService;

	Runnable startChecker = new Runnable() {
		public void run() {
			try {
				weatherCheckerService.startWeatherChecker();
			} catch (ParseException | CityNotFoundException e) {
				LOGGER.error("WeatherChecker -> Error");
			}
		}
	};

	@EventListener(ApplicationReadyEvent.class)
	public void start() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(startChecker, 0, 30, TimeUnit.MINUTES);

	}
}
