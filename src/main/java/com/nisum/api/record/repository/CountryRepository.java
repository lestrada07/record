package com.nisum.api.record.repository;

import com.nisum.api.record.model.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
    @Query("select c.countryCode from CountryEntity c where c.phoneCode = ?1")
    String findByPhoneCode(Integer phoneCode);
}
