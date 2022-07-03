package com.websocket.chat.controller;

import com.websocket.chat.domain.ChatMessage;
import com.websocket.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatMessageController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message, @Header("token") String token) {
        String nickname = jwtTokenProvider.getUerNameFromJwt(token);

        message.setSender(nickname);

        if (ChatMessage.MessageType.ENTER.equals(message.getType()))
            message.setMessage(nickname + "님이 입장하셨습니다.");

        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}