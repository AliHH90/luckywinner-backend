package com.luckywinner.dto;

public class AdminUpdateStatusRequest {

    private String status; // RUNNING / UPCOMING / FINISHED

    public AdminUpdateStatusRequest() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
