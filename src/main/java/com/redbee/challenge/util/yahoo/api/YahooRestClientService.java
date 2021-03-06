package com.redbee.challenge.util.yahoo.api;

import java.io.IOException;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redbee.challenge.exception.CityNotFoundException;
import com.redbee.challenge.exception.YahooApiCallLimitExceededException;
import com.redbee.challenge.exception.YahooApiException;
import com.redbee.challenge.service.ApiCounterService;
import com.redbee.challenge.util.yahoo.api.data.YahooApiResponse;

/**
 * The Yahoo Rest Client Service.
 */
@Service
public class YahooRestClientService {

	/** The Constant URL of API. */
	
	private static final String URL = "http://query.yahooapis.com/v1/public/yql";
	private static Logger LOGGER = LoggerFactory.getLogger(YahooRestClientService.class);

	@Autowired
	ApiCounterService apiCounterService;

	/**
	 * Gets the weather from woeid.
	 *
	 * @param woeid the woeid
	 * @return the actual weather
	 * @throws CityNotFoundException
	 * @throws YahooApiException
	 * @throws YahooApiCallLimitExceededException
	 */
	public YahooApiResponse getWeatherFromWoeid(long woeid)
			throws CityNotFoundException, YahooApiException, YahooApiCallLimitExceededException {
		LOGGER.info("getWeatherFromWoeid: woeid: " + woeid);
		YahooApiResponse yahooApiResponse = new YahooApiResponse();
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
				.queryParam("q", "select * from weather.forecast where woeid=" + woeid + " and u='c'")
				.queryParam("format", "json");

		HttpEntity<?> entity = new HttpEntity<>(headers);

		URI uri = builder.build().encode().toUri();

		if (!apiCounterService.isCallLimitExceededPerDay()) {
			LOGGER.info("subscribe to yahoo's API - Get Wheater:" + uri);
			HttpEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

			ObjectMapper objectMapper = new ObjectMapper();

			;
			try {
				yahooApiResponse = objectMapper.readValue(response.getBody(), YahooApiResponse.class);
			} catch (IOException e) {
				LOGGER.error("Mapping error of the yahoo API response");
			}

			if (yahooApiResponse.getQuery().getResults() == null
					|| yahooApiResponse.getQuery().getResults().getChannel().getLocation() == null) {
				LOGGER.error("Yahoo Api Exception");
				throw new YahooApiException();
			}

		} else {
			throw new YahooApiCallLimitExceededException();
		}

		return yahooApiResponse;

	}

	/**
	 * Gets the woeid from city name.
	 *
	 * @param cityName the city name
	 * @return the woeid from city name
	 * @throws CityNotFoundException              exception for city not found
	 * @throws YahooApiCallLimitExceededException
	 */
	public Long getWoeidFromCityName(String cityName) throws CityNotFoundException, YahooApiCallLimitExceededException {
		LOGGER.info("getWoeidFromCityName: cityName: " + cityName);
		Long woeid = null;

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
				.queryParam("q", "select woeid from geo.places(1) where text='" + cityName + "'")
				.queryParam("format", "json");

		HttpEntity<?> entity = new HttpEntity<>(headers);

		URI uri = builder.build().encode().toUri();

		if (!apiCounterService.isCallLimitExceededPerDay()) {
			LOGGER.info("subscribe to yahoo's API - Get Woeid: " + uri);
			HttpEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

			ObjectMapper objectMapper = new ObjectMapper();
			YahooApiResponse yahooApiResponse = new YahooApiResponse();

			try {
				yahooApiResponse = objectMapper.readValue(response.getBody(), YahooApiResponse.class);
				woeid = yahooApiResponse.getQuery().getResults().getPlace().getWoeid();
			} catch (IOException e) {
				LOGGER.error("Mapping error of the yahoo API response");
			} catch (NullPointerException e) {
				LOGGER.error("City Not Found");
				throw new CityNotFoundException();
			}
		} else {
			throw new YahooApiCallLimitExceededException();
		}

		return woeid;

	}

}
