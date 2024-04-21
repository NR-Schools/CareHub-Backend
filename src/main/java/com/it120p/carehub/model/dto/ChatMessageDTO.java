package com.it120p.carehub.model.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessageDTO {
    private long conversationId;
    private String senderUser;
    private String receiverUser;
    private String messageText;
    private LocalDateTime timestamp;

    // Add this for status check if this is a join or a message
    public enum Status { JOIN, MESSAGE }
    private Status status;
}
