package com.it120p.carehub.model.dto;

import java.util.List;

import com.it120p.carehub.model.entity.UserConversation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserConversationDTO {
    private long conversationId;
    private List<ChatUserDTO> members;
    private List<ChatMessageDTO> messages;

    public static UserConversationDTO fromConversation(UserConversation conversation) {
        return UserConversationDTO.builder()
                .conversationId(conversation.getConversationId())
                .members(
                    conversation
                        .getMembers()
                        .stream()
                        .map(ChatUserDTO::fromUser)
                        .toList()
                )
                .messages(
                    conversation
                        .getMessages()
                        .stream()
                        .map(ChatMessageDTO::fromChatMessage)
                        .toList()
                )
                .build();
    }
}
