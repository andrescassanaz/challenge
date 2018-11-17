package com.redbee.challenge.dto;

public class LocationDto {

	private long woeid;
	private String city;
	private long boardId;

	public long getBoardId() {
		return boardId;
	}

	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public long getWoeid() {
		return woeid;
	}

	public void setWoeid(long woeid) {
		this.woeid = woeid;
	}

}
