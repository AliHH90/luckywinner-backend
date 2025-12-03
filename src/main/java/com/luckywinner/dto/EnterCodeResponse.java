package com.luckywinner.dto;

public class EnterCodeResponse {

    private String status;            // SUCCESS, INVALID_CODE, WINNER_ALREADY_SELECTED, ...
    private String message;           // پیام برگشتی برای کاربر
    private String winnerName;        // نام برنده (اگر قبلاً وجود داشته باشد)
    private String winnerPhoneMasked; // شماره تماس برنده بصورت ماسک شده مثلاً *******5177

    public EnterCodeResponse() {
    }

    public EnterCodeResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public EnterCodeResponse(String status, String message,
                             String winnerName, String winnerPhoneMasked) {
        this.status = status;
        this.message = message;
        this.winnerName = winnerName;
        this.winnerPhoneMasked = winnerPhoneMasked;
    }

    // ---------- Getters & Setters ----------
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public String getWinnerPhoneMasked() {
        return winnerPhoneMasked;
    }

    public void setWinnerPhoneMasked(String winnerPhoneMasked) {
        this.winnerPhoneMasked = winnerPhoneMasked;
    }
}
