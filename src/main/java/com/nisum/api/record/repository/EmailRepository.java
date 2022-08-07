package com.nisum.api.record.repository;

import com.nisum.api.record.model.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmailRepository extends JpaRepository<EmailEntity, Long> {
    @Query("select e from EmailEntity e where e.emailAddress = ?1")
    EmailEntity findByEmailAddress(String email);
}
