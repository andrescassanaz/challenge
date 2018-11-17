package com.redbee.challenge.util;

/**
 * Class to use in rest response
 */
public class RestResponse {

	private Integer responseCode;

	private String message;

	public RestResponse(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public RestResponse(Integer responseCode, String message) {
		this.responseCode = responseCode;
		this.message = message;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
