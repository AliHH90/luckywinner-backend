package com.luckywinner.controller;

import com.luckywinner.dto.WinnerResponse;
import com.luckywinner.service.AdminCompetitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/winners")
@CrossOrigin(origins = "*")
public class WinnerController {

    private final AdminCompetitionService adminCompetitionService;

    public WinnerController(AdminCompetitionService adminCompetitionService) {
        this.adminCompetitionService = adminCompetitionService;
    }

    // GET /api/winners/latest
    @GetMapping("/latest")
    public ResponseEntity<List<WinnerResponse>> getLatestWinners(
            @RequestParam(defaultValue = "20") int limit) {

        List<WinnerResponse> list = adminCompetitionService.getLatestWinners(limit);
        return ResponseEntity.ok(list);
    }
}
