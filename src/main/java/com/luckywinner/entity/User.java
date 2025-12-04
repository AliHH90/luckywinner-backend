package com.luckywinner.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users") // نام جدول در دیتابیس
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // شماره تماس (یونیک)
    @Column(nullable = false, unique = true)
    private String phone;

    // رمز هش‌شده
    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String fullName;

    // USER یا ADMIN
    @Column(nullable = false)
    private String role;

    // موجودی فعلی کیف پول
    @Column(nullable = false)
    private double balance;
    
    private String status;


    // مجموع کل بردها
    @Column(nullable = false)
    private double totalWon;

    // آخرین زمان فعالیت (برای آنلاین بودن)
    private java.time.LocalDateTime lastActiveAt;

    // زمان ثبت‌نام
    @Column(nullable = false)
    private java.time.LocalDateTime createdAt;     // تاریخ ثبت‌نام کاربر
   
	 // داخل کلاس User
	
	 // اضافه شد: آیا کاربر بلاک است؟
	 @Column(nullable = false)
	 private boolean blocked = false;
	
	 public boolean isBlocked() {
	     return blocked;
	 }
	
	 public void setBlocked(boolean blocked) {
	     this.blocked = blocked;
	 }

    
    // سازنده خالی (اجباری برای JPA)
    public User() {
    }

    // سازنده کمکی برای ساخت سریع کاربر جدید
    public User(String phone, String passwordHash, String fullName, String role) {
        this.phone = phone;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.role = role;
        this.balance = 0.0;
        this.totalWon = 0.0;
        this.createdAt = LocalDateTime.now();
    }

    // -------- Getters & Setters --------

    public Long getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(Long id) {
		this.id = id;
	}

	public void setCreatedAt(java.time.LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getTotalWon() {
        return totalWon;
    }

    public void setTotalWon(double totalWon) {
        this.totalWon = totalWon;
    }

    public LocalDateTime getLastActiveAt() {
        return lastActiveAt;
    }

    public void setLastActiveAt(LocalDateTime lastActiveAt) {
        this.lastActiveAt = lastActiveAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    // createdAt را عمداً setter ندادیم که فقط موقع ساخت تنظیم شود
}
