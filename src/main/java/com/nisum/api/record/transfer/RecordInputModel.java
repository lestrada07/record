package com.nisum.api.record.transfer;

import java.util.ArrayList;
import java.util.List;

public class RecordInputModel {

    private String name;
    private String email;
    private String password;
    List<PhoneModel> phones = new ArrayList<>();

    public RecordInputModel(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PhoneModel> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneModel> phones) {
        this.phones = phones;
    }
}
