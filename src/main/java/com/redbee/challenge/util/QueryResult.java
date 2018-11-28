package com.redbee.challenge.util;

import java.util.List;

/**
 * @author Andres Cassanaz
 *
 */
public class QueryResult {

	private RestResponse restResponse;
	private List<Object> queryResponse;

	public QueryResult(RestResponse restResponse, List<Object> queryResponse) {
		this.restResponse = restResponse;
		this.queryResponse = queryResponse;
	}
	
	public QueryResult(RestResponse restResponse) {
		this.restResponse = restResponse;
	}

	public RestResponse getRestResponse() {
		return restResponse;
	}

	public void setRestResponse(RestResponse restResponse) {
		this.restResponse = restResponse;
	}

	public List<Object> getQueryResponse() {
		return queryResponse;
	}

	public void setQueryResponse(List<Object> queryResponse) {
		this.queryResponse = queryResponse;
	}

}
