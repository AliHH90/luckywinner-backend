// src/main/java/com/luckywinner/dto/LeaderboardEntry.java
package com.luckywinner.dto;

public class LeaderboardEntry {

    private Long userId;
    private String fullName;
    private String phone;
    private double totalWon;

    public LeaderboardEntry() {
    }

    public LeaderboardEntry(Long userId, String fullName, String phone, double totalWon) {
        this.userId = userId;
        this.fullName = fullName;
        this.phone = phone;
        this.totalWon = totalWon;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getTotalWon() {
        return totalWon;
    }

    public void setTotalWon(double totalWon) {
        this.totalWon = totalWon;
    }
}
