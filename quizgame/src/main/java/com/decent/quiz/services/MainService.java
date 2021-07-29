package com.decent.quiz.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.decent.quiz.controllers.ChatController;
import com.decent.quiz.daos.MainDao;
import com.decent.quiz.models.MCQs;
import com.decent.quiz.models.Result;
import com.decent.quiz.models.Room;
import com.decent.quiz.models.UserInfo;

@Service
public class MainService {

	private static final Logger LOG = Logger.getLogger(MainService.class.getName());

	private @Autowired MainDao MD;
	private @Autowired ChatController CC;

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

	public MCQs getMCQs(HashSet<Integer> codes) {
		return MD.getMCQs(codes);
	}

	public List<MCQs> listMCQs() {
		return MD.listMCQs();
	}

	public boolean saveResult(UserInfo user, Result result, HashMap<String, Object> response) {
		if (user != null)
			return MD.saveResult(user, result, response);

		response.put("code", HttpStatus.FORBIDDEN.value());
		response.put("msg", "You are not authorized");
		return false;
	}

	public JSONObject getResultAnswers(Result result, HashMap<String, Object> response) {
		return MD.getResultAnswers(result, response);
	}

	public boolean isUserAdded(String username) {
		LOG.info("isUserAdded");
		boolean isMatch = false;
		isMatch = ChatController.rooms.stream().flatMap(r -> r.getUsers().stream())
				.anyMatch(u -> u.getUsername() != null && u.getUsername().compareTo(username) == 0);
		return isMatch;
	}

	public void addNewUser(UserInfo user) {
		LOG.info("addNewUser");
		Room room = new Room();
		room.addUsers(user);
		ChatController.rooms.add(room);
	}

	public void addUser(UserInfo user) {
		LOG.info("ChatController.addUser()");
		boolean isAdd = false, hasVacant = false;
		if (ChatController.rooms != null && !ChatController.rooms.isEmpty()) {
			ChatController.rooms.forEach(r -> LOG.info(r.toString()));

			if (!isUserAdded(user.getUsername())) {
				hasVacant = ChatController.rooms.stream()
						.anyMatch(u -> u.getUsers() != null && !u.getUsers().isEmpty() && u.getUsers().size() < 2);

				if (hasVacant)
					ChatController.rooms.stream()
							.filter(u -> u.getUsers() != null && !u.getUsers().isEmpty() && u.getUsers().size() < 2)
							.forEach(u -> {
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

	public void removeUser(UserInfo user) {
		LOG.info("ChatController.removeUser()");
		if (user != null) {
			ChatController.rooms.forEach(System.out::println);
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

	public HashSet<Integer> getAddedQuestionCode(String lobbyID) {
		if (ChatController.lobbyQuestionCode.containsKey(lobbyID)) {
			return ChatController.lobbyQuestionCode.get(lobbyID);
		}
		return null;
	}

	public void getResultWinner(String lobbyID, HashMap<String, Object> response) {
		MD.getResultWinner(lobbyID, response);
	}

}
