package com.decent.quiz.services;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.decent.quiz.Constants;
import com.decent.quiz.controllers.ChatController;
import com.decent.quiz.daos.MainDao;
import com.decent.quiz.models.MCQs;
import com.decent.quiz.models.Question;
import com.decent.quiz.models.Result;
import com.decent.quiz.models.Room;
import com.decent.quiz.models.UserInfo;

@Service
public class MainService {

	private static final Logger LOG = Logger.getLogger(MainService.class.getName());

	private @Autowired MainDao MD;

	public boolean isRegistered(UserInfo user, HashMap<String, Object> response) {
		boolean status = false;
		status = MD.isExist(user, response);
		if (status) {
			status = MD.isRegistered(user, response);
			if (status) {
				response.put("code", HttpStatus.ACCEPTED.value());
				response.put("msg", "User logged in successfully.");
			} else {
				response.put("code", HttpStatus.NOT_FOUND.value());
				response.put("msg", "Invalid user credentials.");
			}
		} else {
			response.put("code", HttpStatus.UNAUTHORIZED.value());
			response.put("msg",
					String.format("This %s is not yet registered. Please register first.", user.getUsername()));
		}
		return status;
	}

	public boolean register(UserInfo user, HashMap<String, Object> response) {
		boolean status = false;
		if (user != null && user.getUsername() != null && user.getPassword() != null) {
			if (!MD.isExist(user, response)) {
				return MD.registerUser(user, response);
			} else {
				response.put("code", HttpStatus.UNAUTHORIZED.value());
				response.put("msg", "User already registered. Please login instead.");
			}
		} else {
			response.put("code", HttpStatus.NOT_ACCEPTABLE.value());
			response.put("msg", "User details is null");
		}
		return status;
	}

	public boolean isUserLogin(UserInfo user) {
		if (user != null && user.getUsername() != null)
			return true;
		return false;
	}

	/*----------------------------------------------------------------------------------------------------------------------------*/
	public boolean isExistQuestionSequenceId(Question question) {
		boolean isExist = false;
		if (ChatController.rooms != null && !ChatController.rooms.isEmpty()) {
			isExist = ChatController.rooms.stream()
					.filter(r -> r != null && r.getRoomId().equalsIgnoreCase(question.getLobbyID()))
					.flatMap(r -> r.getQuestions().stream()).anyMatch(q -> q != null && q.getSequenceId() != null
							&& q.getSequenceId() == question.getSequenceId());
		}
		return isExist;
	}

	public boolean isUserAdded(String username) {
		LOG.info("isUserAdded");
		boolean isMatch = false;
		isMatch = ChatController.rooms.stream().flatMap(r -> r.getUsers().stream())
				.anyMatch(u -> u.getUsername() != null && u.getUsername().compareTo(username) == 0);
		return isMatch;
	}

	public boolean saveResult(UserInfo user, Result result, HashMap<String, Object> response) {
		if (user != null)
			return MD.saveResult(user, result, response);

		response.put("code", HttpStatus.FORBIDDEN.value());
		response.put("msg", "You are not authorized");
		return false;
	}

	/*----------------------------------------------------------------------------------------------------------------------------*/
	public List<MCQs> listMCQs() {
		return MD.listMCQs();
	}

	public Set<Integer> getAddedQuestionCode(String lobbyID) {

		if (ChatController.rooms != null && !ChatController.rooms.isEmpty()) {
			return ChatController.rooms.stream().filter(r -> r != null && r.getRoomId().equalsIgnoreCase(lobbyID))
					.flatMap(r -> r.getQuestions().stream()).map(Question::getCode).collect(Collectors.toSet());
		}
		return null;
	}

	/*----------------------------------------------------------------------------------------------------------------------------*/

	public Integer getLobbyQuestionCodeBySequenceId(Question question) {
		Integer qcode = null;
		if (ChatController.rooms != null && !ChatController.rooms.isEmpty()) {
			qcode = ChatController.rooms.stream()
					.filter(r -> r != null && r.getRoomId().equalsIgnoreCase(question.getLobbyID()))
					.flatMap(r -> r.getQuestions().stream())
					.filter((q -> q != null && q.getSequenceId() != null
							&& q.getSequenceId() == question.getSequenceId()))
					.map(Question::getCode).findFirst().orElse(null);
		}
		return qcode;
	}

	public JSONObject getResultAnswers(Result result, HashMap<String, Object> response) {
		return MD.getResultAnswers(result, response);
	}

	public MCQs getMCQs(Integer code) {
		return MD.getMCQs(code);
	}

	public MCQs getMCQs(Set<Integer> codes) {
		return MD.getMCQs(codes);
	}

	public Room getLobbyDetails(String lobbyID) {
		Room room = new Room();
		if (ChatController.rooms != null && !ChatController.rooms.isEmpty()) {
			room = ChatController.rooms.stream().filter(r -> r != null && r.getRoomId().equalsIgnoreCase(lobbyID))
					.findFirst().orElse(new Room());
			return room;
		}
		return new Room();
	}

	public String getUserGameLobbyID(String username) {
		LOG.info("ChatController.getUserGameLobbyID()");
		String lobbyID = "";
		if (ChatController.rooms != null && !ChatController.rooms.isEmpty()) {
			lobbyID = ChatController.rooms.stream().filter(r -> {
				if (r != null && r.getUsers() != null && !r.getUsers().isEmpty()) {
					return r.getUsers().stream()
							.anyMatch(u -> u.getUsername() != null && u.getUsername().compareTo(username) == 0);
				}
				return false;
			}).map(Room::getRoomId).findFirst().orElse("");
		}
		return lobbyID;
	}

	/*----------------------------------------------------------------------------------------------------------------------------*/
	public void addNewQuestions(Question question) {
		LOG.info("addNewQuestions");
		if (ChatController.rooms != null && !ChatController.rooms.isEmpty()) {
			ChatController.rooms.stream()
					.filter(r -> r != null && r.getRoomId().equalsIgnoreCase(question.getLobbyID())).forEach(r -> {
						r.addQuestions(question);
					});
		}
	}

	public void addNewUser(UserInfo user) {
		LOG.info("addNewUser");
		Room room = new Room();
		room.addUsers(user);
		ChatController.rooms.add(room);
	}

	public void addUser(UserInfo user) {
		LOG.info("ChatController.addUser()");
		boolean hasVacant = false;
		if (ChatController.rooms != null && !ChatController.rooms.isEmpty()) {
			if (!isUserAdded(user.getUsername())) {
				hasVacant = ChatController.rooms.stream().filter(r -> !r.isStarted()).anyMatch(u -> u.getUsers() != null
						&& !u.getUsers().isEmpty() && u.getUsers().size() < Constants.ROOMSIZE);

				if (hasVacant)
					ChatController.rooms.stream().filter(u -> u.getUsers() != null && !u.getUsers().isEmpty()
							&& u.getUsers().size() < Constants.ROOMSIZE).forEach(u -> {
								u.addUsers(user);
							});

				if (!hasVacant) {
					addNewUser(user);
				}
			}
		} else {
			addNewUser(user);
		}

	}

	public void getResultWinner(String lobbyID, HashMap<String, Object> response) {
		MD.getResultWinner(lobbyID, response);
	}

	public void removeUser(UserInfo user) {
		LOG.info("ChatController.removeUser()");
		if (user != null) {
			if (ChatController.rooms != null && !ChatController.rooms.isEmpty()) {
				ChatController.rooms.forEach(r -> {
					if (r != null && r.getUsers() != null) {
						r.getUsers().removeIf(u -> u.getUsername().equalsIgnoreCase(user.getUsername()));
					}
				});
				ChatController.rooms.removeIf(r -> {
					if (r != null && (r.getUsers() == null || r.getUsers().isEmpty())) {
						return true;
					}
					return false;
				});
			}
		}
	}

	public void startGame(String lobbyID) {
		if (lobbyID != null && ChatController.rooms != null && !ChatController.rooms.isEmpty()) {
			ChatController.rooms.stream().filter(r -> r.getRoomId().equalsIgnoreCase(lobbyID)).forEach(r -> {
				if (!r.isStarted())
					r.setStarted(true);
			});
		}
	}

	/*
	 * private void clearAll(UserInfo user) { if (ChatController.rooms != null &&
	 * !ChatController.rooms.isEmpty()) { Room room = new Room(); String lobbyID =
	 * ""; room = ChatController.rooms.stream().filter(r -> { if (r != null &&
	 * r.getUsers() != null && !r.getUsers().isEmpty()) { return
	 * r.getUsers().stream().anyMatch(u ->
	 * u.getUuid().equalsIgnoreCase(user.getUuid())); } return false;
	 * }).findFirst().orElse(new Room()); if (room != null && room.getRoomId() !=
	 * null) { lobbyID = room.getRoomId();
	 * room.getQuestions().stream().map(Question::getCode).collect(Collectors.toList
	 * ()); } } }
	 */

}
