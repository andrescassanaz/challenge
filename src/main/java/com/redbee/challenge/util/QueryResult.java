package com.redbee.challenge.util;

import java.util.List;

public class QueryResult {

	private RestResponse restResponse;
	private List<Object> queryResponse;

	public QueryResult(RestResponse restResponse, List<Object> list) {
		this.restResponse = restResponse;
		this.queryResponse = list;
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

	public List<Object> getList() {
		return queryResponse;
	}

	public void setList(List<Object> list) {
		this.queryResponse = list;
	}

}
