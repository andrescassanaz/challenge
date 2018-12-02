package com.redbee.challenge.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redbee.challenge.dto.WeatherPointDto;
import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.exception.YahooApiCallLimitExceededException;
import com.redbee.challenge.exception.YahooApiException;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.WeatherPoint;
import com.redbee.challenge.repository.WeatherPointRepository;
import com.redbee.challenge.service.LocationService;
import com.redbee.challenge.service.MapperService;
import com.redbee.challenge.service.WeatherPointService;
import com.redbee.challenge.util.yahoo.api.YahooRestClientService;
import com.redbee.challenge.util.yahoo.api.data.YahooApiResponse;

/**
 * @author Andres Cassanaz
 *
 */
@Service
public class WeatherPointServiceImpl implements WeatherPointService {

	@Autowired
	WeatherPointRepository weatherPointRepository;

	@Autowired
	YahooRestClientService yahooRestClientService;

	@Autowired
	LocationService locationService;

	@Autowired
	MapperService mapperService;

	private static Logger LOGGER = LoggerFactory.getLogger(WeatherPointServiceImpl.class);
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public WeatherPoint save(WeatherPoint weatherPoint) {
		LOGGER.info("save: "+ weatherPoint.getLocation().getCity());
		return weatherPointRepository.save(weatherPoint);
	}

	@Override
	public WeatherPoint findByLocationAndDate(Location location, Long date) {
		LOGGER.info("findByLocationAndDate: location: "+ location.getWoeid() +" "+location.getCity()+", "+location.getCountry()+  " - date: "+date);
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

		int temp = yahooApiResponse.getQuery().getResults().getChannel().getItem().getCondition().getTemp();
		int code = yahooApiResponse.getQuery().getResults().getChannel().getItem().getCondition().getCode();
		String description = yahooApiResponse.getQuery().getResults().getChannel().getItem().getCondition().getText();

		WeatherPoint weatherPoint = new WeatherPoint(locationSaved, System.currentTimeMillis(), temp, code, description);
		return weatherPoint;
	}

	@Override
	public WeatherPoint saveIfNecessary(WeatherPoint weatherPoint) {

		WeatherPoint WeatherPointToReturn = null;

		WeatherPoint weatherPointDB = this.findByLocationAndDate(weatherPoint.getLocation(), weatherPoint.getDate());

		if (weatherPointDB == null) {
			WeatherPointToReturn = this.save(weatherPoint);
		}
		return WeatherPointToReturn;

	}

	public WeatherPoint updateWeatherPointOfLocation(Location location) throws ParseException, CityNotFoundException, YahooApiException, YahooApiCallLimitExceededException {
		LOGGER.info("updateWeatherPointOfLocation: location: "+ location.getWoeid() +" "+location.getCity()+", "+location.getCountry());
		YahooApiResponse weatherResponse = yahooRestClientService.getWeatherFromWoeid(location.getWoeid());
		WeatherPoint weatherPoint = this.buildWeatherPoint(location, weatherResponse);
		this.saveIfNecessary(weatherPoint);
		return weatherPoint;
	}

	@Override
	public List<WeatherPointDto> getWeatherPointByLocation(String woeid) {
		LOGGER.info("getWeatherPointByLocation: location: "+ woeid);
		Location location = locationService.findById(Long.parseLong(woeid));
		Set<WeatherPoint> weatherPointsDb = weatherPointRepository.findByLocation(location);
		List<WeatherPointDto> weatherPointsDto = new ArrayList<WeatherPointDto>();
		for (WeatherPoint weatherPoint : weatherPointsDb) {
			weatherPointsDto.add(mapperService.mapWeatherPointToDto(weatherPoint));
		}
		return weatherPointsDto;
	}

	@Override
	public WeatherPointDto getLastWeatherPointByLocation(String woeid) {
		LOGGER.info("getLastWeatherPointByLocation: location: "+ woeid);
		Location location = locationService.findById(Long.parseLong(woeid));
		WeatherPoint lastWeatherPoint = weatherPointRepository.findFirstByLocationOrderByDateDesc(location);
		return mapperService.mapWeatherPointToDto(lastWeatherPoint);
	}

	@Override
	public List<WeatherPointDto> getWeatherPointByLocationAndDate(String woeid, Long timeInMilliseconds) {
		LOGGER.info("getWeatherPointByLocationAndDate: location: "+ woeid + " - date: "+ timeInMilliseconds);
		Location location = locationService.findById(Long.parseLong(woeid));

		Calendar startOfTheDayCalendar = Calendar.getInstance();
		startOfTheDayCalendar.setTimeInMillis(timeInMilliseconds);
		startOfTheDayCalendar.set(Calendar.MILLISECOND, 0);
		startOfTheDayCalendar.set(Calendar.SECOND, 0);
		startOfTheDayCalendar.set(Calendar.MINUTE, 0);
		startOfTheDayCalendar.set(Calendar.HOUR_OF_DAY, 0);

		Calendar endOfTheDayCalendar = Calendar.getInstance();
		endOfTheDayCalendar.setTimeInMillis(timeInMilliseconds);
		endOfTheDayCalendar.set(Calendar.MILLISECOND, 999);
		endOfTheDayCalendar.set(Calendar.SECOND, 59);
		endOfTheDayCalendar.set(Calendar.MINUTE, 59);
		endOfTheDayCalendar.set(Calendar.HOUR_OF_DAY, 23);
		

		Set<WeatherPoint> weatherPoints = weatherPointRepository.findByLocationAndDate(location,
				startOfTheDayCalendar.getTimeInMillis(), endOfTheDayCalendar.getTimeInMillis());

		List<WeatherPointDto> weatherPointsDto = new ArrayList<WeatherPointDto>();
		for (WeatherPoint weatherPoint : weatherPoints) {
			weatherPointsDto.add(mapperService.mapWeatherPointToDto(weatherPoint));
		}

		return weatherPointsDto;

	}

}
