package com.decent.quiz.configs;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

public class CustomHttpSessionHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		System.out.println("CustomHttpSessionHandshakeInterceptor.beforeHandshake(): Inside");
		try {
			HttpSession session = getSession(request);
			if (session != null) {
				System.out.println("CustomHttpSessionHandshakeInterceptor.beforeHandshake() session not null");
				if (isCopyHttpSessionId()) {
					attributes.put(HTTP_SESSION_ID_ATTR_NAME, session.getId());
				}
				System.out.println("CustomHttpSessionHandshakeInterceptor.beforeHandshake() session.get: "
						+ session.getAttribute("SessionUser"));
				System.out.println(request.getRemoteAddress());
				System.out.println(request.getLocalAddress().toString());
				request.getHeaders().toSingleValueMap().entrySet().forEach(System.out::println);
				Enumeration<String> names = session.getAttributeNames();
				while (names.hasMoreElements()) {
					String name = names.nextElement();
					if (isCopyAllAttributes() || getAttributeNames().contains(name)) {
						attributes.put(name, session.getAttribute(name));
					}
				}
			}
			return true;
		} catch (Exception e) {
			System.out.println("error");
			e.printStackTrace();
		}
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}

	@Nullable
	private HttpSession getSession(ServerHttpRequest request) {
		System.out.println("CustomHttpSessionHandshakeInterceptor.getSession()");
		if (request instanceof ServletServerHttpRequest) {
			System.out.println("CustomHttpSessionHandshakeInterceptor.getSession(): Inside");
			ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
			return serverRequest.getServletRequest().getSession(isCreateSession());
		}
		return null;
	}

	/*
	 * @Override public boolean beforeHandshake(ServerHttpRequest request,
	 * ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object>
	 * attributes) throws Exception {
	 * System.out.println("HttpSessionHandshakeInterceptor.beforeHandshake()"); try
	 * {
	 * 
	 * System.out.println("HttpSessionHandshakeInterceptor.beforeHandshake() Inside"
	 * ); System.out.println("Empty: " + attributes.isEmpty());
	 * System.out.println("Empty: " + attributes.entrySet().toString());
	 * attributes.entrySet().forEach(System.out::println);
	 * System.out.println("HttpSessionHandshakeInterceptor.beforeHandshake() AFTER"
	 * ); } catch (Exception e) { System.out.println("error"); e.printStackTrace();
	 * } System.out.
	 * println("HttpSessionHandshakeInterceptor.beforeHandshake() Outside"); return
	 * true; }
	 * 
	 * @Override public void afterHandshake(ServerHttpRequest request,
	 * ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception)
	 * { System.out.println("HttpSessionHandshakeInterceptor.afterHandshake()");
	 * System.out.println("request.getPrincipal().getName(): " +
	 * request.getPrincipal().getName()); }
	 */

}
