package com.it120p.carehub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.model.entity.Verification;

@Repository
public interface VerificationCodeRepository extends CrudRepository<Verification, Long> {

    @Query("SELECT verify FROM Verification verify WHERE verify.user = ?1")
    Optional<Verification> findVerificationCodeByUser(User user);
}
