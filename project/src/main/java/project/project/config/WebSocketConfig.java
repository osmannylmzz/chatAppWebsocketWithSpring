package project.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.AbstractSubscribableChannel;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.messaging.support.ChannelInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//    private final AuthChannelInterceptor authChannelInterceptor;
//
//    public WebSocketConfig(AuthChannelInterceptor authChannelInterceptor) {
//        this.authChannelInterceptor = authChannelInterceptor;
//    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
        config.enableSimpleBroker("/group", "/user");
        config.setUserDestinationPrefix("/user");//
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();  // WebSocket bağlantısı için endpoint
        registry.addEndpoint("/ws/calls").setAllowedOrigins("*")
                .setAllowedOrigins("https://localhost:63342", "https://localhost:3000", "https://localhost:8443", "http://localhost:8443", "http://localhost:63342", "http://localhost:3000", "http://localhost:63342/project/templates/login", "wss://localhost:8443/ws/calls")// CORS ayarları
                .withSockJS();
//    @Override
//    public void configureClientInboundChannel(org.springframework.messaging.simp.config.ChannelRegistration registration) {
//        registration.interceptors(authChannelInterceptor); // Interceptor'ı bağladık
//    }


    }
}