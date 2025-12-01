package com.luckywinner.dto;

public class AdminOverviewStats {

    private long totalUsers;
    private long onlineUsers;
    private long totalCompetitions;
    private long competitionsToday;
    private double totalPayouts;   // مجموع پرداخت‌ها (PAYOUT)

    public AdminOverviewStats() {
    }

    public AdminOverviewStats(long totalUsers,
                              long onlineUsers,
                              long totalCompetitions,
                              long competitionsToday,
                              double totalPayouts) {
        this.totalUsers = totalUsers;
        this.onlineUsers = onlineUsers;
        this.totalCompetitions = totalCompetitions;
        this.competitionsToday = competitionsToday;
        this.totalPayouts = totalPayouts;
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

    public void setOnlineUsers(long onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public long getTotalCompetitions() {
        return totalCompetitions;
    }

    public void setTotalCompetitions(long totalCompetitions) {
        this.totalCompetitions = totalCompetitions;
    }

    public long getCompetitionsToday() {
        return competitionsToday;
    }

    public void setCompetitionsToday(long competitionsToday) {
        this.competitionsToday = competitionsToday;
    }

    public double getTotalPayouts() {
        return totalPayouts;
    }

    public void setTotalPayouts(double totalPayouts) {
        this.totalPayouts = totalPayouts;
    }
}
