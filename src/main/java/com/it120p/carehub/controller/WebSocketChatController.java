package com.it120p.carehub.controller;

import com.it120p.carehub.model.dto.ChatMessageDTO;
import com.it120p.carehub.model.entity.ChatMessage;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.model.entity.UserConversation;
import com.it120p.carehub.service.UserConversationService;
import com.it120p.carehub.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;


@Controller
public class WebSocketChatController {

    @Autowired
    private UserConversationService userConversationService;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/private-message")
    public void receivePrivateMessage(Authentication authentication, @Payload ChatMessageDTO chatMessageDTO){
        
        // Get user (sender)
        User user = (User) authentication.getPrincipal();

        // Get receiver from chatMessageDTO
        User receiver = userService.getUserByEmail(chatMessageDTO.getReceiverUser().getEmail());

        if (receiver == null) return;

        // Build ChatMessage object
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(user);                                // Use user from auth object
        chatMessage.setReceiver(receiver);                          // Receiver from DTO
        chatMessage.setPayload(chatMessageDTO.getMessageText());    // Message from DTO
        chatMessage.setTimestamp(LocalDateTime.now());              // Timestamp auto gen

        // Get Conversation
        UserConversation userConversation = userConversationService.findConversationWithMembers(List.of(receiver));
        
        // Add message to Conversation
        userConversationService.addMessageToConversation(userConversation.getConversationId(), chatMessage);

        // Construct DTO
        ChatMessageDTO responseChatMessageDTO = ChatMessageDTO.fromChatMessage(chatMessage);

        // Send to Conversation Id
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(userConversation.getConversationId()) ,"/private", responseChatMessageDTO);
    }
}