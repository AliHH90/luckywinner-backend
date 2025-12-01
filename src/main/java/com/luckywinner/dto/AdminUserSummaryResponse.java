package com.luckywinner.dto;

import java.time.LocalDateTime;

public class AdminUserSummaryResponse {

    private Long id;
    private String phone;
    private String fullName;
    private String role;
    private Double balance;
    private Double totalWon;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime lastActiveAt;

    public AdminUserSummaryResponse() {
    }

    public AdminUserSummaryResponse(Long id,
                                    String phone,
                                    String fullName,
                                    String role,
                                    Double balance,
                                    Double totalWon,
                                    String status,
                                    LocalDateTime createdAt,
                                    LocalDateTime lastActiveAt) {
        this.id = id;
        this.phone = phone;
        this.fullName = fullName;
        this.role = role;
        this.balance = balance;
        this.totalWon = totalWon;
        this.status = status;
        this.createdAt = createdAt;
        this.lastActiveAt = lastActiveAt;
    }

    public Long getId() {
        return id;
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

    public Double getBalance() {
        return balance;
    }

    public Double getTotalWon() {
        return totalWon;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastActiveAt() {
        return lastActiveAt;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setTotalWon(Double totalWon) {
        this.totalWon = totalWon;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastActiveAt(LocalDateTime lastActiveAt) {
        this.lastActiveAt = lastActiveAt;
    }
}
