package com.redbee.challenge.util;

import java.text.ParseException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.exception.YahooApiException;
import com.redbee.challenge.service.WeatherCheckerService;

/**
 * @author Andres Cassanaz
 *
 */
@Service
public class WeatherChecker {

	private static Logger LOGGER = LoggerFactory.getLogger(WeatherChecker.class);

	@Autowired
	WeatherCheckerService weatherCheckerService;
	
	@Value("${weather.checker.scheduled.task.interval}")
	private int weatherCheckerScheduledTaskInterval;

	Runnable startChecker = new Runnable() {
		public void run() {
			
				try {
					weatherCheckerService.startWeatherChecker();
				} catch (ParseException e) {
					LOGGER.error("ParseException");					
				} catch (CityNotFoundException e) {
					LOGGER.error("CityNotFoundException");
				} catch (YahooApiException e) {
					LOGGER.error("YahooApiException");
				}
			
		}
	};

	/**
	 * Starts a task every x minutes to review the current climate and update it
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void start() {
		LOGGER.info("Start WeatherChecker scheduled task");
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(startChecker, 0, weatherCheckerScheduledTaskInterval, TimeUnit.MINUTES);

	}
}
