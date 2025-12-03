package com.luckywinner.dto;

public class MeResponse {

    private String phone;
    private String fullName;
    private String role;
    private double balance;
    private double totalWon;

    public MeResponse(String phone, String fullName, String role, double balance, double totalWon) {
        this.phone = phone;
        this.fullName = fullName;
        this.role = role;
        this.balance = balance;
        this.totalWon = totalWon;
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

    public double getBalance() {
        return balance;
    }

    public double getTotalWon() {
        return totalWon;
    }
}
