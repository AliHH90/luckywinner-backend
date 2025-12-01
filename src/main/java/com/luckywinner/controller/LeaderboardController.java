// src/main/java/com/luckywinner/controller/LeaderboardController.java
package com.luckywinner.controller;

import com.luckywinner.dto.LeaderboardEntry;
import com.luckywinner.service.LeaderboardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @GetMapping("/top")
    public List<LeaderboardEntry> getTop(
            @RequestParam(name = "limit", defaultValue = "10") int limit
    ) {
        return leaderboardService.getTopWinners(limit);
    }
}
