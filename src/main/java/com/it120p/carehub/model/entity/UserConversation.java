package com.it120p.carehub.model.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_conversations")
public class UserConversation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long conversationId;

    @ManyToMany(
        fetch = FetchType.EAGER
    )
    @JoinTable(
        name = "conversation_member_mapping",
        joinColumns = @JoinColumn(name = "user_conversation_id"), 
        inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private List<User> members = List.of();

    @ManyToMany(
        fetch = FetchType.EAGER
    )
    @JoinTable(
        name = "conversation_messages_mapping",
        joinColumns = @JoinColumn(name = "user_conversation_id"), 
        inverseJoinColumns = @JoinColumn(name = "message_id")
    )
    private List<ChatMessage> messages = List.of();
}
