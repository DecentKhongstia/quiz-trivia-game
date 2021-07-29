//package com.decent.quiz.controllers;
//
//import java.security.Principal;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.messaging.simp.annotation.SendToUser;
//import org.springframework.stereotype.Controller;
//
//import com.decent.quiz.Constants;
//import com.decent.quiz.models.Message;
//import com.decent.quiz.models.OutputMessage;
//
//@Controller
//public class WebSocketController {
//
//	private static final java.util.logging.Logger LOG = java.util.logging.Logger
//			.getLogger(WebSocketController.class.getName());
//
//	/*
//	 * @Autowired private SimpMessagingTemplate simpMessagingTemplate;
//	 */
//
//	@Autowired
//	private SimpMessageSendingOperations simpMessageSendingOperations;
//
//	@MessageMapping(Constants.SECURED_CHAT)
//	@SendTo(Constants.SECURED_CHAT_HISTORY)
//	public OutputMessage sendAll(Message msg) throws Exception {
//		LOG.info("sendAll: " + Constants.SECURED_CHAT + " Message: " + msg);
//		System.out.println("WebSocketController.sendAll() " + Constants.SECURED_CHAT + " Message: " + msg);
//		OutputMessage out = new OutputMessage(msg.getFrom(), msg.getText(),
//				new SimpleDateFormat("HH:mm").format(new Date()));
//		return out;
//	}
//
//	/**
//	 * Example of sending message to specific user using 'convertAndSendToUser()'
//	 * and '/queue'
//	 */
//	@MessageMapping(Constants.SECURED_CHAT_ROOM)
//	public void sendSpecific(@Payload Message msg, Principal user, @Header("simpSessionId") String sessionId)
//			throws Exception {
//		LOG.info("sendSpecific: " + Constants.SECURED_CHAT_ROOM + " Message: " + msg + " Principal: " + user
//				+ " Sessionid: " + sessionId);
//		System.out.println("WebSocketController.sendSpecific() " + Constants.SECURED_CHAT_ROOM + " Message: " + msg
//				+ " Principal: " + user + " Sessionid: " + sessionId);
//		OutputMessage out = new OutputMessage(msg.getFrom(), msg.getText(),
//				new SimpleDateFormat("HH:mm").format(new Date()));
//		System.out.println("WebSocketController.sendSpecific(): " + out.toString());
//
//		simpMessageSendingOperations.convertAndSendToUser(msg.getTo(), Constants.SECURED_CHAT_SPECIFIC_USER, out);
//
//	}
//
//	/*
//	 * @MessageMapping("/chat")
//	 * 
//	 * @SendTo("/topic/messages") public OutputMessage greeting(Message message)
//	 * throws Exception { System.out.println("greeting - message: " + message);
//	 * return new OutputMessage(message.getFrom(), message.getText(), new
//	 * SimpleDateFormat("HH:mm").format(new Date())); }
//	 * 
//	 * @MessageMapping("/chat2") public void greeting2(@Payload Message message)
//	 * throws Exception { System.out.println("greeting2 - message: " + message);
//	 * simpMessageSendingOperations.convertAndSend("/topic/messages", message); }
//	 */
//	/*
//	 * @MessageMapping(value = { "/message", "/greeting" })
//	 * 
//	 * @SendToUser("/queue/reply") public OutputMessage
//	 * processMessageFromClient(@Payload Message message, Principal principal)
//	 * throws Exception { LOG.info("processMessageFromClient: " + message); return
//	 * new OutputMessage(message.getFrom(), message.getText(), new
//	 * SimpleDateFormat("HH:mm").format(new Date())); }
//	 */
//	@MessageExceptionHandler
//	@SendToUser("/queue/errors")
//	public String handleException(Throwable exception) {
//		LOG.info("handleException: " + exception.getMessage());
//		exception.printStackTrace();
//		return exception.getMessage();
//	}
//
//}
