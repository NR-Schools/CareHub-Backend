package com.it120p.carehub.model.dto;

import java.time.LocalDateTime;

import com.it120p.carehub.model.entity.ChatMessage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessageDTO {
    private long messageId;
    private ChatUserDTO senderUser;
    private ChatUserDTO receiverUser;
    private String messageText;
    private LocalDateTime timestamp;

    public static ChatMessageDTO fromChatMessage(ChatMessage chatMessage) {
        return ChatMessageDTO.builder()
                .messageId(chatMessage.getMessageId())
                .senderUser(ChatUserDTO.fromUser(chatMessage.getSender()))
                .receiverUser(ChatUserDTO.fromUser(chatMessage.getReceiver()))
                .messageText(chatMessage.getPayload())
                .timestamp(chatMessage.getTimestamp())
                .build();
    }
}
