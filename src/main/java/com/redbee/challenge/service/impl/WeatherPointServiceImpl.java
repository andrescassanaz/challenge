package com.redbee.challenge.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redbee.challenge.dto.WeatherPointDto;
import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.model.Location;
import com.redbee.challenge.model.WeatherPoint;
import com.redbee.challenge.repository.WeatherPointRepository;
import com.redbee.challenge.service.LocationService;
import com.redbee.challenge.service.MapperService;
import com.redbee.challenge.service.WeatherPointService;
import com.redbee.challenge.util.Utils;
import com.redbee.challenge.util.yahoo.api.YahooRestClientService;
import com.redbee.challenge.util.yahoo.api.data.YahooApiResponse;

@Service
public class WeatherPointServiceImpl implements WeatherPointService {

	@Autowired
	WeatherPointRepository weatherPointRepository;

	@Autowired
	Utils utils;

	@Autowired
	YahooRestClientService yahooRestClientService;

	@Autowired
	LocationService locationService;

	@Autowired
	MapperService mapperService;

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
		int code = yahooApiResponse.getQuery().getResults().getChannel().getItem().getCondition().getCode();
		String description = yahooApiResponse.getQuery().getResults().getChannel().getItem().getCondition().getText();

		WeatherPoint weatherPoint = new WeatherPoint(locationSaved, date.getTimeInMillis(), temp, code, description);
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

	public WeatherPoint updateWeatherPointOfLocation(Location location) throws ParseException, CityNotFoundException {
		YahooApiResponse weatherResponse = yahooRestClientService.getWeatherFromWoeid(location.getWoeid());
		WeatherPoint weatherPoint = this.buildWeatherPoint(location, weatherResponse);
		System.out.println("Intentando guardar:" + location.getCity());
		this.saveIfNecessary(weatherPoint);
		return weatherPoint;
	}

	@Override
	public List<WeatherPointDto> getWeatherPointByLocation(String woeid) {
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
		Location location = locationService.findById(Long.parseLong(woeid));
		WeatherPoint lastWeatherPoint = weatherPointRepository.findFirstByLocationOrderByDateDesc(location);
		return mapperService.mapWeatherPointToDto(lastWeatherPoint);
	}

}
