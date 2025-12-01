package com.luckywinner.dto;

import java.time.LocalDateTime;

public class AdminCreateCompetitionRequest {

    private String title;
    private String description;
    private int roundNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status; // OPTIONAL: اگر خالی باشد RUNNING می‌گذاریم
    private String correctCode; // کد جایزه
    public String getCorrectCode() {
		return correctCode;
	}

	public void setCorrectCode(String correctCode) {
		this.correctCode = correctCode;
	}

	public AdminCreateCompetitionRequest() {
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
