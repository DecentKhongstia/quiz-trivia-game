package com.decent.quiz.models;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MCQs {

	private boolean display = false;

	private Integer code;
	
	private String description;
	private String response;
	
	private List<MCAs> answers;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public List<MCAs> getAnswers() {
		return answers;
	}

	public void setAnswers(List<MCAs> answers) {
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "MCQs [code=" + code + ", description=" + description + ", display=" + display + ", answers=" + answers
				+ "]";
	}

}
