package com.nisum.api.record.repository;

import com.nisum.api.record.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("select u from UserEntity u where u.userId = ?1")
    UserEntity findByUIID(String uuid);
}

