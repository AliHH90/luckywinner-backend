package com.luckywinner.dto;

import java.time.LocalDateTime;

public class ParticipantResponse {

    private Long userId;
    private String phone;
    private String fullName;
    private boolean answeredCorrect;
    private boolean watchedAd;
    private boolean winner;
    private LocalDateTime enterTime;

    public ParticipantResponse() {
    }

    public ParticipantResponse(Long userId,
                               String phone,
                               String fullName,
                               boolean answeredCorrect,
                               boolean watchedAd,
                               boolean winner,
                               LocalDateTime enterTime) {
        this.userId = userId;
        this.phone = phone;
        this.fullName = fullName;
        this.answeredCorrect = answeredCorrect;
        this.watchedAd = watchedAd;
        this.winner = winner;
        this.enterTime = enterTime;
    }

    public Long getUserId() {
        return userId;
    }

    public String getPhone() {
        return phone;
    }

    public String getFullName() {
        return fullName;
    }

    public boolean isAnsweredCorrect() {
        return answeredCorrect;
    }

    public boolean isWatchedAd() {
        return watchedAd;
    }

    public boolean isWinner() {
        return winner;
    }

    public LocalDateTime getEnterTime() {
        return enterTime;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAnsweredCorrect(boolean answeredCorrect) {
        this.answeredCorrect = answeredCorrect;
    }

    public void setWatchedAd(boolean watchedAd) {
        this.watchedAd = watchedAd;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public void setEnterTime(LocalDateTime enterTime) {
        this.enterTime = enterTime;
    }
}
