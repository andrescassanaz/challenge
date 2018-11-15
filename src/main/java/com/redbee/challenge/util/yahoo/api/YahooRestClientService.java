package com.redbee.challenge.util.yahoo.api;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redbee.challenge.util.yahoo.api.data.YahooApiResponse;

@Service
public class YahooRestClientService {

	private static final String URL = "http://query.yahooapis.com/v1/public/yql";

	public YahooApiResponse getConditionFromWoeid(long woeid) {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
				.queryParam("q", "select item.condition from weather.forecast where woeid="+woeid)
				.queryParam("format", "json");

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity,
				String.class);

		System.out.println(response.getBody());

		ObjectMapper objectMapper = new ObjectMapper();
		YahooApiResponse yahooApiResponse = new YahooApiResponse();;
		try {
			yahooApiResponse = objectMapper.readValue(response.getBody(), YahooApiResponse.class);
			System.out.println("SE CONSUMIO SERVICIO REST DE YAHOO:");
			System.out.println("WOEID: "+woeid);
			System.out.println("CODE: "+yahooApiResponse.getQuery().getResults().getChannel().getItem().getCondition().getCode());
			System.out.println("DATE: "+yahooApiResponse.getQuery().getResults().getChannel().getItem().getCondition().getDate());
			System.out.println("TEMP: "+yahooApiResponse.getQuery().getResults().getChannel().getItem().getCondition().getTemp());
			System.out.println("TEXT: "+yahooApiResponse.getQuery().getResults().getChannel().getItem().getCondition().getText());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return yahooApiResponse;

	}

}
