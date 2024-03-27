package com.it120p.carehub.repository;

import com.it120p.carehub.model.entity.Request;
import com.it120p.carehub.model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {
}
