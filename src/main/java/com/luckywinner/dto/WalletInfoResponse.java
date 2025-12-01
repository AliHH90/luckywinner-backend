// src/main/java/com/luckywinner/dto/WalletInfoResponse.java
package com.luckywinner.dto;

public class WalletInfoResponse {

    private double balance;
    private double totalWon;

    public WalletInfoResponse() {
    }

    public WalletInfoResponse(double balance, double totalWon) {
        this.balance = balance;
        this.totalWon = totalWon;
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
}
