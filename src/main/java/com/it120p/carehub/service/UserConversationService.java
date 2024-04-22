package com.it120p.carehub.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.it120p.carehub.model.entity.ChatMessage;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.model.entity.UserConversation;
import com.it120p.carehub.repository.ChatMessageRepository;
import com.it120p.carehub.repository.UserConversationRepository;

@Service
public class UserConversationService {
    
    @Autowired
    private UserConversationRepository userConversationRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public UserConversation findConversationWithMembers(List<User> members) {

        // Get all conversations
        List<UserConversation> userConversations = Streamable.of(userConversationRepository.findAll()).toList();

        // Find conversation with provided members
        Optional<UserConversation> optConversation = userConversations.stream()
            .filter(userConversation -> {
                List<User> membersExt = userConversation.getMembers();
                
                // Compare by userId
                List<Long> userIds_members = membersExt
                                                .stream()
                                                .map(member -> member.getUserId())
                                                .toList();
                List<Long> userIds_attempts  = members
                                                .stream()
                                                .map(member -> member.getUserId())
                                                .toList();
                
                // Compare
                return userIds_members
                        .stream()
                        .filter(e -> !userIds_attempts.contains(e))
                        .toList()
                        .isEmpty()
                        &&
                       userIds_attempts
                        .stream()
                        .filter(e -> !userIds_members.contains(e))
                        .toList()
                        .isEmpty();

            })
            .findFirst();
            
        // Create new if empty
        if (optConversation.isEmpty()) {
            UserConversation userConversation = new UserConversation();
            userConversation.setMembers(members);
            return userConversationRepository.save(userConversation);
        }

        return optConversation.get();
    }

    public ChatMessage addMessageToConversation(long conversationId, ChatMessage chatMessage) {

        // Try getting conversation by id
        Optional<UserConversation> optionalConversation = userConversationRepository.findById(conversationId);

        // If conversation doesn't exist, do not proceed
        if (optionalConversation.isEmpty()) return null;

        UserConversation conversation = optionalConversation.get();

        // Perform null-check on messages list
        if (conversation.getMessages() == null) {
            conversation.setMessages(List.of());
        }

        // Save Message
        ChatMessage savedChatMessage = chatMessageRepository.save(chatMessage);

        // Add chat message
        conversation.getMessages().add(chatMessage);

        // Save Conversation
        userConversationRepository.save(conversation);

        // Return chat message
        return savedChatMessage;
    }
}
