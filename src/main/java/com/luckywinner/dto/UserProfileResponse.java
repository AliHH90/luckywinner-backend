package com.luckywinner.dto;

import java.time.LocalDateTime;

public class UserProfileResponse {

    private Long id;
    private String phone;
    private String fullName;
    private String role;
    private String status;          // ✅ اضافه شد
    private Double balance;
    private Double totalWon;
    private LocalDateTime createdAt;   // ✅ اضافه شد
    private LocalDateTime lastActiveAt; // اگر خواستی بعداً پرش کنیم (اختیاری)

    public UserProfileResponse() {
    }

    // سازنده‌ای که الان در UserService استفاده می‌کنیم
    public UserProfileResponse(Long id,
                               String phone,
                               String fullName,
                               String role,
                               String status,
                               Double balance,
                               Double totalWon,
                               LocalDateTime createdAt) {
        this.id = id;
        this.phone = phone;
        this.fullName = fullName;
        this.role = role;
        this.status = status;        // ✅ حالا فیلدش وجود دارد
        this.balance = balance;
        this.totalWon = totalWon;
        this.createdAt = createdAt;  // ✅ حالا فیلدش وجود دارد
    }

    // --------- getter / setter ها ----------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getTotalWon() {
        return totalWon;
    }

    public void setTotalWon(Double totalWon) {
        this.totalWon = totalWon;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastActiveAt() {
        return lastActiveAt;
    }

    public void setLastActiveAt(LocalDateTime lastActiveAt) {
        this.lastActiveAt = lastActiveAt;
    }
}
