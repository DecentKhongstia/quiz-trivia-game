package com.decent.quiz;

public class Constants {
	public static final String SECURED_CHAT_HISTORY = "/secured/history";
	public static final String SECURED_CHAT = "/secured/chat";
	public static final String SECURED_CHAT_ROOM = "/secured/room";
	public static final String SECURED_CHAT_SPECIFIC_USER = "/secured/user/queue/specific-user";

	public static final String SESSION_USER = "SessionUser";
	public static final String USER_UUID = "UUID";
	public static final String USERNAME = "Username";

	public static final Integer ROOMSIZE = 10;
	public static final Integer NOOFQUESTIONS = 3;
	
	public static final String PATH_END_SCREEN = "game-end";
	public static final String PATH_GAME_SCREEN = "game-screen";
	public static final String PATH_LOBBY_SCREEN = "game-lobby";
	public static final String PATH_LOGIN_SCREEN = "login";
	public static final String PATH_REGISTER_SCREEN = "register";
	public static final String PATH_REDIRECT_SCREEN = "redirect:login";
	public static final String PATH_QUESTION_SCREEN = "game-question";
	public static final String PATH_RESULT_SCREEN = "game-result";
	
	public static final String PATH_FIND_LOBBY_MESSAGE = "find-lobby";
	public static final String PATH_JOIN_LOBBY_MESSAGE = "join-lobby";
	public static final String PATH_GET_QUESTION_MESSAGE = "get-question";
	public static final String PATH_GET_ANSWER_MESSAGE = "get-answer";
	
	public static final String POST_LOGIN = "login";
	public static final String POST_REGISTER = "register";
	public static final String POST_SAVERESULT = "save-result";
	public static final String POST_GETANSWERS = "get-answers";
	public static final String GET_GETWINNER = "get-winner/{lobbyID}";
	
	public static final String DESTINATION_LOBBY_ID= "/queue/lobbyID";
	public static final String DESTINATION_LOBBY_ANSWERS = "/queue/lobby-answers";
	public static final String DESTINATION_LOBBY_DETAILS = "/queue/lobby-details";
	public static final String DESTINATION_LOBBY_QUESTIONS = "/queue/lobby-questions";

}
