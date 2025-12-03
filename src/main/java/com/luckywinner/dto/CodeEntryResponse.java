package com.luckywinner.dto;

public class CodeEntryResponse {

    private boolean success;          // موفق بود یا نه
    private String message;           // پیام برای کاربر
    private String winnerName;        // نام برنده (اگر قبلا مشخص شده باشد)
    private String winnerPhoneMasked; // شماره برنده به صورت ماسک‌شده (****5177 مثلا)

    public CodeEntryResponse() {
    }

    public CodeEntryResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public CodeEntryResponse(boolean success,
                             String message,
                             String winnerName,
                             String winnerPhoneMasked) {
        this.success = success;
        this.message = message;
        this.winnerName = winnerName;
        this.winnerPhoneMasked = winnerPhoneMasked;
    }

    // ---------- Getters & Setters ----------

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
