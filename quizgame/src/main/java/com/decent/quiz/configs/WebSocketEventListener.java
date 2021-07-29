package com.decent.quiz.configs;

import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.decent.quiz.Constants;
import com.decent.quiz.controllers.ChatController;
import com.decent.quiz.controllers.MainController;
import com.decent.quiz.models.UserInfo;

@Component
public class WebSocketEventListener {

	private static final Logger LOG = Logger.getLogger(MainController.class.getName());
	
	@Autowired
	ChatController cc; 
	
	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		LOG.info("Received a new web socket connection");
	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		LOG.info("WebSocketEventListener.handleWebSocketDisconnectListener()");
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

		UserInfo user = (UserInfo) headerAccessor.getSessionAttributes().get(Constants.SESSION_USER);
		if (user != null) {
			/* LOG.info("User Disconnected : " + user.getUsername()); */
			
			/*
			 * ChatMessage chatMessage = new ChatMessage();
			 * chatMessage.setType(ChatMessage.MessageType.LEAVE);
			 * chatMessage.setSender(username);
			 * 
			 * messagingTemplate.convertAndSend("/topic/public", chatMessage);
			 */
		}
	}
}
