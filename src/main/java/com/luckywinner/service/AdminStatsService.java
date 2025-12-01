package com.luckywinner.service;

import com.luckywinner.dto.AdminOverviewStats;
import com.luckywinner.repository.CompetitionRepository;
import com.luckywinner.repository.UserRepository;
import com.luckywinner.repository.WalletTransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AdminStatsService {

    private final UserRepository userRepository;
    private final CompetitionRepository competitionRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    public AdminStatsService(UserRepository userRepository,
                             CompetitionRepository competitionRepository,
                             WalletTransactionRepository walletTransactionRepository) {
        this.userRepository = userRepository;
        this.competitionRepository = competitionRepository;
        this.walletTransactionRepository = walletTransactionRepository;
    }

    public AdminOverviewStats getOverviewStats() {

        // تعداد کل کاربران
        long totalUsers = userRepository.count();

        // کاربران آنلاین: کسانی که در ۵ دقیقه اخیر lastActiveAt داشته‌اند
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(5);
        long onlineUsers = userRepository.countByLastActiveAtAfter(cutoff);

        // تعداد کل مسابقات
        long totalCompetitions = competitionRepository.count();

        // مسابقات امروز
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        long competitionsToday =
                competitionRepository.countByStartTimeBetween(startOfDay, endOfDay);

        // مجموع پرداخت‌ها (نوع PAYOUT)
        double totalPayouts = walletTransactionRepository.sumAmountByType("PAYOUT");

        return new AdminOverviewStats(
                totalUsers,
                onlineUsers,
                totalCompetitions,
                competitionsToday,
                totalPayouts
        );
    }
}
