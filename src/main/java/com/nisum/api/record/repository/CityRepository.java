package com.nisum.api.record.repository;

import com.nisum.api.record.model.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CityRepository extends JpaRepository<CityEntity, Long> {
    @Query("select c from CityEntity c where c.phoneCode = ?1 and c.countryCode =?2")
    CityEntity findByPhoneCodeAndCountryCode(Integer phoneCode, String countryCode);

}
