package com.decent.quiz.models;

public class Answer {

	private String lobbyID;
	private String username;
	private Integer tally;
	private Integer code;
	private String response;

	public String getLobbyID() {
		return lobbyID;
	}

	public void setLobbyID(String lobbyID) {
		this.lobbyID = lobbyID;
	}

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

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "Answer [lobbyID=" + lobbyID + ", username=" + username + ", tally=" + tally + "]";
	}

}
