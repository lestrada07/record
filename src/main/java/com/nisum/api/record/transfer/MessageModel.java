package com.nisum.api.record.transfer;

public class MessageModel {

    private String message;

    public MessageModel() {

    }

    public MessageModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
