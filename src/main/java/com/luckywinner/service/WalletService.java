// src/main/java/com/luckywinner/service/WalletService.java
package com.luckywinner.service;

import com.luckywinner.dto.WalletInfoResponse;
import com.luckywinner.dto.WalletTransactionDto;
import com.luckywinner.entity.User;
import com.luckywinner.entity.WalletTransaction;
import com.luckywinner.repository.UserRepository;
import com.luckywinner.repository.WalletTransactionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final UserRepository userRepository;

    public WalletService(WalletTransactionRepository walletTransactionRepository,
                         UserRepository userRepository) {
        this.walletTransactionRepository = walletTransactionRepository;
        this.userRepository = userRepository;
    }

    // گرفتن کاربر لاگین شده
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("No authenticated user");
        }
        String phone = auth.getName();
        User user = userRepository.findByPhone(phone);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        user.setLastActiveAt(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }

    // خلاصه کیف پول کاربر
    public WalletInfoResponse getMyWalletInfo() {
        User user = getCurrentUser();
        return new WalletInfoResponse(
                user.getBalance(),
                user.getTotalWon()
        );
    }

    // متن توضیح بر اساس نوع تراکنش
    private String buildDescription(WalletTransaction tx) {
        String type = tx.getType();
        if ("WIN".equalsIgnoreCase(type)) {
            return "برنده جایزه شدی";
        } else if ("PAYOUT".equalsIgnoreCase(type)) {
            return "برداشت از کیف پول";
        } else if ("DEPOSIT".equalsIgnoreCase(type)) {
            return "شارژ کیف پول";
        }
        return "تراکنش کیف پول";
    }

    // لیست آخرین تراکنش‌های کاربر
    public List<WalletTransactionDto> getMyTransactions() {
        User user = getCurrentUser();

        List<WalletTransaction> txList =
                walletTransactionRepository.findTop30ByUserOrderByCreatedAtDesc(user);

        return txList.stream()
                .map(tx -> new WalletTransactionDto(
                        buildDescription(tx),
                        tx.getAmount(),
                        tx.getType(),
                        tx.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}
