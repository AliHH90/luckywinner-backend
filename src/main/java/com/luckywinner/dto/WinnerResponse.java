package com.luckywinner.dto;

import java.time.LocalDateTime;

public class WinnerResponse {

    private Long userId;
    private String phone;
    private String fullName;
    private Double prize;
    private Long competitionId;
    private String competitionTitle;
    private LocalDateTime enterTime;

    public WinnerResponse() {
    }

    public WinnerResponse(Long userId,
                          String phone,
                          String fullName,
                          Double prize,
                          Long competitionId,
                          String competitionTitle,
                          LocalDateTime enterTime) {
        this.userId = userId;
        this.phone = phone;
        this.fullName = fullName;
        this.prize = prize;
        this.competitionId = competitionId;
        this.competitionTitle = competitionTitle;
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

    public Double getPrize() {
        return prize;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public String getCompetitionTitle() {
        return competitionTitle;
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

    public void setPrize(Double prize) {
        this.prize = prize;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public void setCompetitionTitle(String competitionTitle) {
        this.competitionTitle = competitionTitle;
    }

    public void setEnterTime(LocalDateTime enterTime) {
        this.enterTime = enterTime;
    }
}
