package com.websocket.chat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@RequiredArgsConstructor
@EnableWebSocketMessageBroker // 웹소켓 활성화 + Stomp 사용
@Configuration // 이게 없으니까 왜 안되지...? 이건 @Bean 을 등록할 때 사용되는 어노테이션인데..
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // ?
        registry.enableSimpleBroker("/sub");
        // @MessageRequest 로 라우팅
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*")
                .withSockJS();
    }

    // StompHandler 의 token 확인을 위해 인터셉터로 지정
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
}
