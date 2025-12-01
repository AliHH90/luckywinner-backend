// src/main/java/com/luckywinner/service/LeaderboardService.java
package com.luckywinner.service;

import com.luckywinner.dto.LeaderboardEntry;
import com.luckywinner.entity.User;
import com.luckywinner.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeaderboardService {

    private final UserRepository userRepository;

    public LeaderboardService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<LeaderboardEntry> getTopWinners(int limit) {

        if (limit <= 0) {
            limit = 10;
        }

        // حداکثر 100 تا برای جلوگیری از مصرف زیاد
        if (limit > 100) {
            limit = 100;
        }

        List<User> users = userRepository.findTop10ByOrderByTotalWonDesc();

        if (users.size() > limit) {
            users = users.subList(0, limit);
        }

        List<LeaderboardEntry> result = new ArrayList<>();

        for (User u : users) {
            LeaderboardEntry entry = new LeaderboardEntry(
                    u.getId(),
                    u.getFullName(),
                    u.getPhone(),
                    u.getTotalWon()
            );
            result.add(entry);
        }

        return result;
    }
}
