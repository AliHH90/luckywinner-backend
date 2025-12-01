package com.luckywinner.dto;

public class AdminStatsResponse {

	private long totalUsers;
    private long onlineUsers;
    private long totalCompetitions;
    private long competitionsToday;
    private double totalPayout;
    
    public AdminStatsResponse() {
    }

    public AdminStatsResponse(long totalUsers, int onlineUsers, int competitionsToday, double totalPayout) {
        this.totalUsers = totalUsers;
        this.onlineUsers = onlineUsers;
        this.competitionsToday = competitionsToday;
        this.totalPayout = totalPayout;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(int onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public long getCompetitionsToday() {
        return competitionsToday;
    }

    public void setCompetitionsToday(int competitionsToday) {
        this.competitionsToday = competitionsToday;
    }

    public double getTotalPayout() {
        return totalPayout;
    }

    public void setTotalPayout(double totalPayout) {
        this.totalPayout = totalPayout;
    }
}
