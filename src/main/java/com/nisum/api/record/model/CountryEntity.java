package com.nisum.api.record.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "COUNTRY")
public class CountryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE_CODE")
    private int phoneCode;

    public CountryEntity(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(int phoneCode) {
        this.phoneCode = phoneCode;
    }
}
