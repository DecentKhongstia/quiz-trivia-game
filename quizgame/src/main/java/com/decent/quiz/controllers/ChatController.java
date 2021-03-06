package com.decent.quiz.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.decent.quiz.Constants;
import com.decent.quiz.models.Answer;
import com.decent.quiz.models.MCQs;
import com.decent.quiz.models.Message;
import com.decent.quiz.models.OutputMessage;
import com.decent.quiz.models.Question;
import com.decent.quiz.models.Room;
import com.decent.quiz.models.UserInfo;
import com.decent.quiz.services.MainService;

@Controller
public class ChatController {

	private static final Logger LOG = Logger.getLogger(ChatController.class.getName());

	public static HashMap<String, String> sessions = new HashMap<String, String>();

	public static ArrayList<Room> rooms = new ArrayList<Room>();
	/*
	 * public static HashMap<String, HashSet<Integer>> lobbyQuestionCode = new
	 * HashMap();
	 */
	/*
	 * public static HashMap<String, HashSet<Answer>> lobbyAnswers = new HashMap();
	 */
	/* public static HashMap<Integer, MCQs> lobbyQuestions = new HashMap(); */

	@Autowired
	private MainService MS;

	@Autowired
	private SimpMessageSendingOperations simpMessageSendingOperations;

	@MessageMapping("/chat")
	public void send(final Message message) throws Exception {
		System.out.println("V2-ChatController.send(): message " + message);
		final String time = new SimpleDateFormat("HH:mm").format(new Date());
		OutputMessage out = new OutputMessage(message.getFrom(), message.getText(), time);
		simpMessageSendingOperations.convertAndSend("/topic/messages", out);
	}

	@MessageMapping("/message")
	public void sendToUser(final @Payload Message message, SimpMessageHeaderAccessor sha,
			@Header("simpSessionId") String sessionId) throws Exception {
		try {
			final String time = new SimpleDateFormat("HH:mm").format(new Date());

			String to = "NA";

			UserInfo user = (UserInfo) sha.getSessionAttributes().get(Constants.SESSION_USER);
			MS.addUser(user);

			OutputMessage out = new OutputMessage(message.getFrom(), message.getText(), time);

			if (!sessions.containsKey(message.getFrom()))
				sessions.put(sha.getUser().getName(), message.getFrom());

			sessions.entrySet().forEach(System.out::println);
			if (!sessions.isEmpty())
				to = sessions.get(message.getTo());

			simpMessageSendingOperations.convertAndSendToUser(to, "/queue/reply", out);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@MessageMapping(value = Constants.PATH_FIND_LOBBY_MESSAGE)
	public void getGameLobbyID(final @Payload Message message, SimpMessageHeaderAccessor sha,
			@Header("simpSessionId") String sessionId) throws Exception {
		try {
			LOG.info("ChatController.getGameLobbyID()");
			LOG.info("Message: " + message);

			final String time = new SimpleDateFormat("HH:mm").format(new Date());
			String lobbyID = "";
			UserInfo user = (UserInfo) sha.getSessionAttributes().get(Constants.SESSION_USER);
			MS.addUser(user);
			lobbyID = MS.getUserGameLobbyID(user.getUsername());
			simpMessageSendingOperations.convertAndSendToUser(message.getFrom(), Constants.DESTINATION_LOBBY_ID,
					lobbyID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@MessageMapping(value = Constants.PATH_JOIN_LOBBY_MESSAGE)
	public void joinGameLobby(final @Payload UserInfo user, SimpMessageHeaderAccessor sha,
			@Header("simpSessionId") String sessionId) throws Exception {
		try {
			LOG.info("ChatController.joinGameLobby()");
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			String lobbyID = "";
			Room room = new Room();
			lobbyID = user.getLobbyID();
			room = MS.getLobbyDetails(lobbyID);
			JSONObject out = new JSONObject();
			out.put("time", room.getTime());
			out.put("users", room.getUsers());
			simpMessageSendingOperations.convertAndSendToUser(lobbyID, Constants.DESTINATION_LOBBY_DETAILS, out);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@MessageMapping(value = Constants.PATH_GET_QUESTION_MESSAGE)
	public void getQuestion(final @Payload Question question, SimpMessageHeaderAccessor sha,
			@Header("simpSessionId") String sessionId) throws Exception {
		try {
			LOG.info("ChatController.getQuestion()");
			LOG.info("Question: "+question);
			MCQs qs = new MCQs();
			if (MS.isExistQuestionSequenceId(question)) {
				LOG.info("SEQUENCE ID EXIST");
				qs = MS.getMCQs(MS.getLobbyQuestionCodeBySequenceId(question));
			} else {
				LOG.info("SEQUENCE ID NOT EXIST");
				Set<Integer> questioncodes = MS.getAddedQuestionCode(question.getLobbyID());
				qs = MS.getMCQs(questioncodes);
				question.setCode(qs.getCode());
				MS.addNewQuestions(question);
			}
			LOG.info("MCQS: "+qs);
			if (qs != null) {
				MS.startGame(question.getLobbyID());
				simpMessageSendingOperations.convertAndSendToUser(question.getUuid(),
						Constants.DESTINATION_LOBBY_QUESTIONS, qs);
			}
		} catch (Exception e) {

		}
	}

	/*
	 * @MessageMapping(value = Constants.PATH_GET_ANSWER_MESSAGE) public void
	 * getAnswer(final @Payload Answer answer, SimpMessageHeaderAccessor sha,
	 * 
	 * @Header("simpSessionId") String sessionId) throws Exception {
	 * LOG.info("ChatController.getAnswer()"); try { if (answer != null &&
	 * answer.getUsername() != null && answer.getLobbyID() != null) { if
	 * (!lobbyAnswers.containsKey(answer.getLobbyID())) {
	 * lobbyAnswers.put(answer.getLobbyID(), new HashSet<Answer>()); } if
	 * (!lobbyAnswers.containsKey(answer.getLobbyID())) {
	 * lobbyAnswers.get(answer.getLobbyID()).add(answer);
	 * simpMessageSendingOperations.convertAndSendToUser(answer.getLobbyID(),
	 * Constants.DESTINATION_LOBBY_ANSWERS, lobbyAnswers.get(answer.getLobbyID()));
	 * } } } catch (Exception e) { e.printStackTrace(); } }
	 */
}
