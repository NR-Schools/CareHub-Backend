package com.it120p.carehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.it120p.carehub.model.dto.UserConversationDTO;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.service.UserConversationService;
import com.it120p.carehub.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private UserConversationService userConversationService;

    @Autowired
    private UserService userService;

    @GetMapping("/history")
    public UserConversationDTO getConversation(
            Authentication authentication,
            @RequestParam("otherUserEmail") String otherUserEmail
    ) {
        // Get User from Authentication
        User user = (User) authentication.getPrincipal();

        // Get otherUser by email
        User otherUser = userService.getUserByEmail(otherUserEmail);
        
        // Find conversation for that user
        return UserConversationDTO.fromConversation(
                userConversationService.findConversationWithMembers(
                List.of(user, otherUser)
            )
        );
    }
}
