package com.luckywinner.dto;

public class UpdateFullNameRequest {

    private String fullName;

    public UpdateFullNameRequest() {
    }

    public UpdateFullNameRequest(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
