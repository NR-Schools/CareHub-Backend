package com.it120p.carehub.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.it120p.carehub.model.entity.ChatMessage;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.model.entity.UserConversation;
import com.it120p.carehub.repository.UserConversationRepository;

@Service
public class UserConversationService {
    
    @Autowired
    private UserConversationRepository userConversationRepository;

    public UserConversation findConversationWithMembers(List<User> members) {

        // Get all conversations
        List<UserConversation> userConversations = Streamable.of(userConversationRepository.findAll()).toList();

        // Find conversation with provided members
        Optional<UserConversation> optConversation = userConversations.stream()
            .filter(userConversation -> userConversation.getMember().containsAll(members))
            .findFirst();
        
        // Create new if empty
        if (optConversation.isEmpty()) {
            UserConversation userConversation = new UserConversation();
            userConversation.setMember(members);
            return userConversationRepository.save(userConversation);
        }

        return optConversation.get();
    }

    public void addMessageToConversation(long conversationId, ChatMessage chatMessage) {

        // Try getting conversation by id
        Optional<UserConversation> optionalConversation = userConversationRepository.findById(conversationId);

        // If conversation doesn't exist, do not proceed
        if (optionalConversation.isEmpty()) return;

        UserConversation conversation = optionalConversation.get();

        // Perform null-check on messages list
        if (conversation.getMessages() == null) {
            conversation.setMessages(List.of());
        }

        // Add chat message
        conversation.getMessages().add(chatMessage);

        // Save Conversation
        userConversationRepository.save(conversation);
    }
}
