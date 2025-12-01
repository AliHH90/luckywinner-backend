// src/main/java/com/luckywinner/service/AdminDashboardService.java
package com.luckywinner.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luckywinner.dto.AdminDashboardSummaryResponse;
import com.luckywinner.entity.Competition;
import com.luckywinner.entity.User;
import com.luckywinner.entity.WalletTransaction;
import com.luckywinner.repository.CompetitionRepository;
import com.luckywinner.repository.UserRepository;
import com.luckywinner.repository.WalletTransactionRepository;

@Service
public class AdminDashboardService {

    private final UserRepository userRepository;
    private final CompetitionRepository competitionRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    public AdminDashboardService(UserRepository userRepository,
                                 CompetitionRepository competitionRepository,
                                 WalletTransactionRepository walletTransactionRepository) {
        this.userRepository = userRepository;
        this.competitionRepository = competitionRepository;
        this.walletTransactionRepository = walletTransactionRepository;
    }

    public AdminDashboardSummaryResponse getSummary() {

        // ----- Users -----
        List<User> users = userRepository.findAll();
        long totalUsers = users.size();

        long totalAdmins = users.stream()
                .filter(u -> u.getRole() != null
                          && u.getRole().equalsIgnoreCase("ADMIN"))
                .count();

        long totalNonAdmins = totalUsers - totalAdmins;

        // مجموع موجودی کیف پول همه‌ی کاربران
        double totalWalletBalance = users.stream()
                // getBalance معمولا primitive double است → نیازی به null-check نیست
                .mapToDouble(User::getBalance)
                .sum();

        // ----- Competitions -----
        List<Competition> comps = competitionRepository.findAll();
        long totalCompetitions = comps.size();

        long runningCompetitions = comps.stream()
                .filter(c -> c.getStatus() != null
                          && c.getStatus().equalsIgnoreCase("RUNNING"))
                .count();

        long finishedCompetitions = comps.stream()
                .filter(c -> c.getStatus() != null
                          && c.getStatus().equalsIgnoreCase("FINISHED"))
                .count();

        // ----- Wallet Transactions (فقط WIN) -----
        List<WalletTransaction> txs = walletTransactionRepository.findAll();

        double totalPrizePaid = txs.stream()
                .filter(t -> t.getType() != null
                          && t.getType().equalsIgnoreCase("WIN"))
                // اینجا getAmount باید primitive double باشد
                .mapToDouble(WalletTransaction::getAmount)
                .sum();

        return new AdminDashboardSummaryResponse(
                totalUsers,
                totalAdmins,
                totalNonAdmins,
                totalCompetitions,
                runningCompetitions,
                finishedCompetitions,
                totalWalletBalance,
                totalPrizePaid
        );
    }
}
