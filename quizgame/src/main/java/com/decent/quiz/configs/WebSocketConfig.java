package com.decent.quiz.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic", "/user", "/queue");
		config.setApplicationDestinationPrefixes("/app");
		config.setUserDestinationPrefix("/user");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("chat").withSockJS();
		/*
		 * registry.addEndpoint("chat").setHandshakeHandler(new
		 * CustomHandshakeHandler()).withSockJS();
		 */

		registry.addEndpoint("chat2").setHandshakeHandler(new CustomHandshakeHandler())
				.addInterceptors(new HttpSessionHandshakeInterceptor()).withSockJS();

		/*
		 * registry.addEndpoint("chat2").addInterceptors(new
		 * CustomHttpSessionHandshakeInterceptor()). withSockJS();
		 */
		registry.addEndpoint("chatwithbots").withSockJS();
	}

	/*
	 * @EventListener void handleSessionConnectedEvent(SessionConnectedEvent event)
	 * { // Get Accessor
	 * System.out.println("handleSessionConnectedEvent: Message- "+event.getMessage(
	 * ));
	 * System.out.println("handleSessionConnectedEvent: User- "+event.getUser());
	 * StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage()); }
	 */

}
