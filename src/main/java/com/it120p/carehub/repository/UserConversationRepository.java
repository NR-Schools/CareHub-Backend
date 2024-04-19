package com.it120p.carehub.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.it120p.carehub.model.entity.UserConversation;

@Repository
public interface UserConversationRepository extends CrudRepository<UserConversation, Long> {
}
