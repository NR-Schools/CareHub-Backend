package com.it120p.carehub.controller;

import org.springframework.web.bind.annotation.RestController;

import com.it120p.carehub.model.entity.ChatMessage;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.model.entity.UserConversation;
import com.it120p.carehub.service.UserConversationService;
import com.it120p.carehub.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/chat")
public class TempChatController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConversationService userConversationService;

    @GetMapping
    public UserConversation getConversation(
            Authentication authentication,
            @RequestParam("otherUserEmail") String otherUserEmail
    ) {
        // Get User from Authentication
        User user = (User) authentication.getPrincipal();

        // Get otherUser by email
        User otherUser = userService.getUserByEmail(otherUserEmail);

        // Find conversation for that user
        return userConversationService.findConversationWithMembers(
            List.of(user, otherUser)
        );
    }

    @PostMapping
    public void addMessagebyUser(
        Authentication authentication,
        @RequestParam("otherUserEmail") String otherUserEmail,
        @RequestPart("chatMessage") ChatMessage chatMessage
    ) {
        // Get User from Authentication
        User user = (User) authentication.getPrincipal();

        // Get otherUser by email
        User otherUser = userService.getUserByEmail(otherUserEmail);

        // Find conversation for that user
        UserConversation userConversation = userConversationService.findConversationWithMembers(
            List.of(user, otherUser)
        );

        // Add Chat Message
        userConversationService.addMessageToConversation(userConversation.getConversationId(), chatMessage);
    }
    

}
