package com.nisum.api.record.repository;

import com.nisum.api.record.model.EmailEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

@DataJpaTest
public class EmailRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EmailRepository emailRepository;

    EmailEntity emailEntity;

    @Test
    void testFindEmail(){
        String email = "jose@gmail.com";

        Assertions.assertEquals(emailEntity, emailRepository.findByEmailAddress(email));
    }
}
