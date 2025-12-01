// src/main/java/com/luckywinner/dto/WalletTransactionDto.java
package com.luckywinner.dto;

import java.time.LocalDateTime;

public class WalletTransactionDto {

    private String description;
    private double amount;
    private String type;
    private LocalDateTime createdAt;

    public WalletTransactionDto() {
    }

    public WalletTransactionDto(String description,
                                double amount,
                                String type,
                                LocalDateTime createdAt) {
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
