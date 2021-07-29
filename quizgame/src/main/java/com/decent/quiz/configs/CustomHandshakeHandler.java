package com.decent.quiz.configs;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.decent.quiz.Constants;
import com.decent.quiz.models.StompPrincipal;
import com.decent.quiz.models.UserInfo;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {
	// Custom class for storing principal
	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		System.out.println("CustomHandshakeHandler.determineUser()");
		attributes.entrySet().forEach(System.out::println);
		UserInfo user = (UserInfo) attributes.get(Constants.SESSION_USER);
		System.out.println("user: "+user);
		// Generate principal with UUID as name
		return new StompPrincipal(user.getUsername());
	}
}
