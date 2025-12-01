package com.luckywinner.dto;

public class AdminDashboardSummaryResponse {

    private long totalUsers;
    private long totalAdmins;
    private long totalNonAdmins;

    private long totalCompetitions;
    private long runningCompetitions;
    private long finishedCompetitions;

    private double totalWalletBalance;
    private double totalPrizePaid;

    // ----------- Constructor کامل --------------
    public AdminDashboardSummaryResponse(long totalUsers,
                                         long totalAdmins,
                                         long totalNonAdmins,
                                         long totalCompetitions,
                                         long runningCompetitions,
                                         long finishedCompetitions,
                                         double totalWalletBalance,
                                         double totalPrizePaid) {
        this.totalUsers = totalUsers;
        this.totalAdmins = totalAdmins;
        this.totalNonAdmins = totalNonAdmins;
        this.totalCompetitions = totalCompetitions;
        this.runningCompetitions = runningCompetitions;
        this.finishedCompetitions = finishedCompetitions;
        this.totalWalletBalance = totalWalletBalance;
        this.totalPrizePaid = totalPrizePaid;
    }

    // ----------- Getter / Setter ها --------------

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalAdmins() {
        return totalAdmins;
    }

    public void setTotalAdmins(long totalAdmins) {
        this.totalAdmins = totalAdmins;
    }

    public long getTotalNonAdmins() {
        return totalNonAdmins;
    }

    public void setTotalNonAdmins(long totalNonAdmins) {
        this.totalNonAdmins = totalNonAdmins;
    }

    public long getTotalCompetitions() {
        return totalCompetitions;
    }

    public void setTotalCompetitions(long totalCompetitions) {
        this.totalCompetitions = totalCompetitions;
    }

    public long getRunningCompetitions() {
        return runningCompetitions;
    }

    public void setRunningCompetitions(long runningCompetitions) {
        this.runningCompetitions = runningCompetitions;
    }

    public long getFinishedCompetitions() {
        return finishedCompetitions;
    }

    public void setFinishedCompetitions(long finishedCompetitions) {
        this.finishedCompetitions = finishedCompetitions;
    }

    public double getTotalWalletBalance() {
        return totalWalletBalance;
    }

    public void setTotalWalletBalance(double totalWalletBalance) {
        this.totalWalletBalance = totalWalletBalance;
    }

    public double getTotalPrizePaid() {
        return totalPrizePaid;
    }

    public void setTotalPrizePaid(double totalPrizePaid) {
        this.totalPrizePaid = totalPrizePaid;
    }
}
