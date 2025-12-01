package com.luckywinner.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckywinner.dto.AdminCreateCompetitionRequest;
import com.luckywinner.dto.AdminQuestionRequest;
import com.luckywinner.dto.AdminSetCodeRequest;
import com.luckywinner.dto.AdminUpdateStatusRequest;
import com.luckywinner.dto.CompetitionResponse;
import com.luckywinner.dto.ParticipantResponse;
import com.luckywinner.dto.QuestionResponse;
import com.luckywinner.service.AdminCompetitionService;

@RestController
@RequestMapping("/api/admin/competitions")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCompetitionController {

    private final AdminCompetitionService adminCompetitionService;

    public AdminCompetitionController(AdminCompetitionService adminCompetitionService) {
        this.adminCompetitionService = adminCompetitionService;
    }

    // POST /api/admin/competitions
    @PostMapping
    public ResponseEntity<CompetitionResponse> createCompetition(
            @RequestBody AdminCreateCompetitionRequest request) {

        CompetitionResponse response = adminCompetitionService.createCompetition(request);
        return ResponseEntity.ok(response);
    }

    // GET /api/admin/competitions
    @GetMapping
    public ResponseEntity<List<CompetitionResponse>> getAll() {
        List<CompetitionResponse> list = adminCompetitionService.getAllCompetitions();
        return ResponseEntity.ok(list);
    }

    // PUT /api/admin/competitions/{id}/correct-code
    @PutMapping("/{id}/correct-code")
    public ResponseEntity<Void> setCorrectCode(@PathVariable Long id,
                                               @RequestBody AdminSetCodeRequest request) {
        adminCompetitionService.setCorrectCode(id, request.getCorrectCode());
        return ResponseEntity.ok().build();
    }

    // PUT /api/admin/competitions/{id}/status
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id,
                                             @RequestBody AdminUpdateStatusRequest request) {
        adminCompetitionService.updateStatus(id, request.getStatus());
        return ResponseEntity.ok().build();
    }

    // GET /api/admin/competitions/{id}/participants
    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantResponse>> getParticipants(@PathVariable Long id) {
        List<ParticipantResponse> list = adminCompetitionService.getParticipants(id);
        return ResponseEntity.ok(list);
    }

    // GET /api/admin/competitions/{id}/question
    @GetMapping("/{id}/question")
    public ResponseEntity<QuestionResponse> getQuestion(@PathVariable Long id) {
        QuestionResponse response = adminCompetitionService.getQuestion(id);
        return ResponseEntity.ok(response);
    }

    // PUT /api/admin/competitions/{id}/question
    @PutMapping("/{id}/question")
    public ResponseEntity<QuestionResponse> saveQuestion(@PathVariable Long id,
                                                         @RequestBody AdminQuestionRequest request) {
        QuestionResponse response = adminCompetitionService.saveQuestion(id, request);
        return ResponseEntity.ok(response);
    }
}
