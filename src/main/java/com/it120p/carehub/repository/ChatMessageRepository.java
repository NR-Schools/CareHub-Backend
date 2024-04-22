package com.it120p.carehub.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.it120p.carehub.model.entity.ChatMessage;

@Repository
public interface ChatMessageRepository extends CrudRepository<ChatMessage, Long> {
}
