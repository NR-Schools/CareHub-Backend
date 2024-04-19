package com.it120p.carehub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.model.entity.VerificationCode;

@Repository
public interface VerificationCodeRepository extends CrudRepository<VerificationCode, Long> {

    @Query("SELECT verify FROM VerificationCode verify WHERE verify.user = ?1")
    Optional<VerificationCode> findVerificationCodeByUser(User user);
}
