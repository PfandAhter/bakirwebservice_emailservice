package com.bakirwebservice.emailservice.repository;

import com.bakirwebservice.emailservice.entity.EmailValidator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailValidatorRepository extends JpaRepository<EmailValidator,String> {

    @Query("select ev from EmailValidator ev where ev.emailValidatorId = ?1 and ev.username = ?2")
    EmailValidator findEmailValidatorByEmailValidatorIdAndUsername(String emailValidatorId , String username);

    @Query("select ev from EmailValidator ev where ev.username = ?1")
    EmailValidator findUserValidateCodeByUsername (String username);
}
