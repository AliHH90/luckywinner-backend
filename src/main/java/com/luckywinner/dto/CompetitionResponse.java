package com.luckywinner.dto;

import java.time.LocalDateTime;

public class CompetitionResponse {

    private Long id;
    private String title;
    private String description;
    private int roundNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;

    public CompetitionResponse() {
    }

    public CompetitionResponse(Long id, String title, String description,
                               int roundNumber, LocalDateTime startTime,
                               LocalDateTime endTime, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.roundNumber = roundNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    // getters & setters ...

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
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
