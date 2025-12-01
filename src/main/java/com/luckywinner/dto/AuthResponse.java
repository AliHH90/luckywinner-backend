package com.luckywinner.dto;

public class AuthResponse {

    private String token;
    private String phone;
    private String fullName;
    private String role;

    public AuthResponse() {
    }

    public AuthResponse(String token, String phone, String fullName, String role) {
        this.token = token;
        this.phone = phone;
        this.fullName = fullName;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getPhone() {
        return phone;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
