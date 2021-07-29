package com.decent.quiz.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Winner {

	private String username;
	private Integer tally;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getTally() {
		return tally;
	}

	public void setTally(Integer tally) {
		this.tally = tally;
	}

	@Override
	public String toString() {
		return "Winner [username=" + username + ", tally=" + tally + "]";
	}

}
