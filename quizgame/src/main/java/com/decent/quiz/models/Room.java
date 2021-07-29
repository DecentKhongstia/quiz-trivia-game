package com.decent.quiz.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.decent.quiz.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Room {

	private String roomId;
	private String time;
	private ArrayList<MCQs> questions = new ArrayList<MCQs>(Constants.NOOFQUESTIONS);
	private ArrayList<UserInfo> users = new ArrayList<UserInfo>(Constants.ROOMSIZE);

	public Room() {
		this.roomId = UUID.randomUUID().toString();
	}

	public String getRoomId() {
		return this.roomId;
	}

	public String getTime() {
		return time;
	}

	public ArrayList<UserInfo> getUsers() {
		return users;
	}

	public void addUsers(UserInfo user) {
		if (users == null || users.isEmpty() || users.size() < Constants.ROOMSIZE)
			this.users.add(user);
		if (users != null && !users.isEmpty() && users.size() > 1 && this.time == null) {
			Calendar cal = Calendar.getInstance();
		    Date date=cal.getTime();
		    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		    this.time = dateFormat.format(date);			
		}
	}

	
	public ArrayList<MCQs> getQuestions() {
		return questions;
	}
	
	public void setQuestions(ArrayList<MCQs> questions) {
		this.questions = questions;
	}

	public void addQuestions(MCQs question) {
		if (this.questions != null && !this.questions.isEmpty()) {
			boolean exist = this.questions.stream()
					.anyMatch(q -> q != null && q.getCode() != null && q.getCode() == question.getCode());
			if (!exist)
				this.questions.add(question);
		} else
			this.questions.add(question);
	}

	@Override
	public String toString() {
		return "Room [roomId=" + roomId + ", time=" + time + ", questions=" + questions + ", users=" + users + "]";
	}

	/*
	 * @Override public String toString() { return "Room [roomId= " + roomId +
	 * " time= " + time + "getUsers()=" + getUsers().toString() + "]"; }
	 */

}
