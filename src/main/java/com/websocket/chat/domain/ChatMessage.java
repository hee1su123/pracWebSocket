package com.websocket.chat.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    // 입장, 채팅
    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}
