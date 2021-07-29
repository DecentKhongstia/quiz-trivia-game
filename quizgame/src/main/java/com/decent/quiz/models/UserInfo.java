package com.decent.quiz.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo implements Serializable {

	private String uuid;
	private String username;
	private String password;

	private String lobbyID;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLobbyID() {
		return lobbyID;
	}

	public void setLobbyID(String lobbyID) {
		this.lobbyID = lobbyID;
	}

	@Override
	public String toString() {
		return "UserInfo [uuid=" + uuid + ", username=" + username + ", lobbyID=" + lobbyID + "]";
	}

}
