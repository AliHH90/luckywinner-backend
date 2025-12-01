package com.luckywinner.controller;

import java.util.Collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckywinner.dto.AnswerRequest;
import com.luckywinner.dto.AnswerResponse;
import com.luckywinner.dto.CodeEntryRequest;
import com.luckywinner.dto.CodeEntryResponse;
import com.luckywinner.dto.CompetitionResponse;
import com.luckywinner.dto.QuestionResponse;
import com.luckywinner.service.CompetitionService;

@RestController
@RequestMapping("/api/competitions")
@CrossOrigin(origins = "*")
public class CompetitionController {

    private final CompetitionService competitionService;

    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    // GET /api/competitions/current
    @GetMapping("/current")
    public ResponseEntity<CompetitionResponse> getCurrentCompetition() {
        CompetitionResponse response = competitionService.getCurrentCompetition();
        return ResponseEntity.ok(response);
    }

    // GET /api/competitions/current/question
    @GetMapping("/current/question")
    public ResponseEntity<QuestionResponse> getCurrentQuestion() {
        QuestionResponse response = competitionService.getCurrentQuestion();
        return ResponseEntity.ok(response);
    }

    // POST /api/competitions/current/answer
    @PostMapping("/current/answer")
    public ResponseEntity<?> answerCurrent(@RequestBody AnswerRequest request) {
        try {
            AnswerResponse response = competitionService.answerCurrentQuestion(request);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException ex) {
            // قبلاً در مسابقه شرکت کرده
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("message", ex.getMessage()));
        }
    }

    // -----------------------------
    // ❌ Endpoint مربوط به تبلیغ (فعلاً غیرفعال)
    // -----------------------------
    /*
    @PostMapping("/current/ad-watched")
    public ResponseEntity<CodeEntryResponse> adWatched() {
        CodeEntryResponse response = competitionService.markAdWatchedForCurrentCompetition();
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    */

    // ✅ این یکی فعال می‌ماند (برنده شدن با وارد کردن کد)
    @PostMapping("/current/enter-code")
    public ResponseEntity<CodeEntryResponse> enterCode(@RequestBody CodeEntryRequest request) {
        CodeEntryResponse response = competitionService.enterCorrectCode(request.getCode());
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

}
