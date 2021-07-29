package com.decent.quiz.models;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

	private String lobbyID;
	private String username;
	private boolean winner = false;
	private Integer tally;
	private ArrayList<Answer> answers;

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

	public boolean isWinner() {
		return winner;
	}

	public Integer getTally() {
		return tally;
	}

	public void setTally(Integer tally) {
		this.tally = tally;
	}

	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}

	@Override
	public String toString() {
		return "Result [lobbyID=" + lobbyID + ", username=" + username + ", tally=" + tally + "]";
	}

}
