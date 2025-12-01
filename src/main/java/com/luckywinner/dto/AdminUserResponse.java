// src/main/java/com/luckywinner/dto/AdminUserResponse.java
package com.luckywinner.dto;

import java.time.LocalDateTime;

public class AdminUserResponse {

    private Long id;
    private String phone;
    private String fullName;
    private double balance;
    private double totalWon;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime lastActiveAt;

    public AdminUserResponse() {
    }

    public AdminUserResponse(Long id,
                             String phone,
                             String fullName,
                             double balance,
                             double totalWon,
                             String role,
                             LocalDateTime createdAt,
                             LocalDateTime lastActiveAt) {
        this.id = id;
        this.phone = phone;
        this.fullName = fullName;
        this.balance = balance;
        this.totalWon = totalWon;
        this.role = role;
        this.createdAt = createdAt;
        this.lastActiveAt = lastActiveAt;
    }

    // ===== getters & setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public double getTotalWon() { return totalWon; }
    public void setTotalWon(double totalWon) { this.totalWon = totalWon; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getLastActiveAt() { return lastActiveAt; }
    public void setLastActiveAt(LocalDateTime lastActiveAt) { this.lastActiveAt = lastActiveAt; }
}
