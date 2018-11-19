package com.redbee.challenge.service.impl;

import java.text.ParseException;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.WeatherPoint;
import com.redbee.challenge.repository.WeatherPointRepository;
import com.redbee.challenge.service.WeatherPointService;
import com.redbee.challenge.util.Utils;
import com.redbee.challenge.util.yahoo.api.data.YahooApiResponse;

@Service
public class WeatherPointServiceImpl implements WeatherPointService {

	@Autowired
	WeatherPointRepository weatherPointRepository;

	@Autowired
	Utils utils;

	@Override
	public WeatherPoint save(WeatherPoint weatherPoint) {
		return weatherPointRepository.save(weatherPoint);
	}

	@Override
	public WeatherPoint findByLocationAndDate(Location location, Long date) {
		return weatherPointRepository.findByLocationAndDate(location, date);
	}

	/**
	 * Builds the weather history.
	 *
	 * @param locationSaved    the location saved
	 * @param yahooApiResponse the yahoo api response
	 * @return Weather History PointW
	 * @throws ParseException
	 */
	@Override
	public WeatherPoint buildWeatherPoint(Location locationSaved, YahooApiResponse yahooApiResponse)
			throws ParseException {

		Calendar date = utils.parseStringToCalendar(
				yahooApiResponse.getQuery().getResults().getChannel().getItem().getCondition().getDate());

		int temp = yahooApiResponse.getQuery().getResults().getChannel().getItem().getCondition().getTemp();
		String description = yahooApiResponse.getQuery().getResults().getChannel().getItem().getCondition().getText();

		WeatherPoint weatherPoint = new WeatherPoint(locationSaved, date.getTimeInMillis(), temp, description);
		return weatherPoint;
	}

	@Override
	public WeatherPoint saveIfNecessary(WeatherPoint weatherPoint) {

		WeatherPoint WeatherPointToReturn = null;

		WeatherPoint weatherPointDB = this.findByLocationAndDate(weatherPoint.getLocation(),
				weatherPoint.getDate());

		if (weatherPointDB == null) {
			WeatherPointToReturn = this.save(weatherPoint);
		}
		return WeatherPointToReturn;

	}

}
