package com.nisum.api.record.transfer;

import org.springframework.stereotype.Component;

@Component
public class ResponseModel {

    private String status;
    private String statusMessage;
    private Object data;

    public ResponseModel(){

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
