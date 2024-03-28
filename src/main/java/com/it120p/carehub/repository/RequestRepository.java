package com.it120p.carehub.repository;

import com.it120p.carehub.model.entity.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {

    @Query("SELECT r FROM Request r WHERE r.userId = ?1")
    List<Request> findRequestByUserId(long userId);
}
