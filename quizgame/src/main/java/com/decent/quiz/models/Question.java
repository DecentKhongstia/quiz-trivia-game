package com.decent.quiz.models;

public class Question {

	private Integer code;
	private String lobbyID;
	private Integer sequenceId;
	private String uuid;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getLobbyID() {
		return lobbyID;
	}

	public void setLobbyID(String lobbyID) {
		this.lobbyID = lobbyID;
	}

	public Integer getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "Question [code=" + code + ", lobbyID=" + lobbyID + "]";
	}

}
