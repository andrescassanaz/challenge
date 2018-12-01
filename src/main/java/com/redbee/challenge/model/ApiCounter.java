package com.redbee.challenge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ApiCounter {

	@Column
	@Id
	private Integer id;
	
	@Column
	private Integer apiCalls;
	
	@Column
	private Long date;

	public ApiCounter() {
	}

	public Integer getApiCalls() {
		return apiCalls;
	}

	public void setApiCalls(Integer apiCalls) {
		this.apiCalls = apiCalls;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
