package com.luckywinner.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    // ایجاد مسابقه
    @PostMapping
    public ResponseEntity<CompetitionResponse> createCompetition(
            @RequestBody AdminCreateCompetitionRequest request) {

        CompetitionResponse response = adminCompetitionService.createCompetition(request);
        return ResponseEntity.ok(response);
    }

    // لیست مسابقات
    @GetMapping
    public ResponseEntity<List<CompetitionResponse>> getAll() {
        List<CompetitionResponse> list = adminCompetitionService.getAllCompetitions();
        return ResponseEntity.ok(list);
    }

    // تنظیم کد صحیح
    @PutMapping("/{id}/correct-code")
    public ResponseEntity<Void> setCorrectCode(@PathVariable Long id,
                                               @RequestBody AdminSetCodeRequest request) {
        adminCompetitionService.setCorrectCode(id, request.getCorrectCode());
        return ResponseEntity.ok().build();
    }

    // تغییر وضعیت مسابقه
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id,
                                             @RequestBody AdminUpdateStatusRequest request) {
        adminCompetitionService.updateStatus(id, request.getStatus());
        return ResponseEntity.ok().build();
    }

    // گرفتن لیست شرکت‌کنندگان
    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantResponse>> getParticipants(@PathVariable Long id) {
        List<ParticipantResponse> list = adminCompetitionService.getParticipants(id);
        return ResponseEntity.ok(list);
    }

    // گرفتن سوال مسابقه
    @GetMapping("/{id}/question")
    public ResponseEntity<QuestionResponse> getQuestion(@PathVariable Long id) {
        QuestionResponse response = adminCompetitionService.getQuestion(id);
        return ResponseEntity.ok(response);
    }

    // ذخیره/ویرایش سوال مسابقه
    @PutMapping("/{id}/question")
    public ResponseEntity<QuestionResponse> saveQuestion(@PathVariable Long id,
                                                         @RequestBody AdminQuestionRequest request) {
        QuestionResponse response = adminCompetitionService.saveQuestion(id, request);
        return ResponseEntity.ok(response);
    }

    // حذف فقط سوال‌های مسابقه
    @DeleteMapping("/{id}/question")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        adminCompetitionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    // حذف کامل مسابقه + سوال + مشارکت‌ها
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetition(@PathVariable Long id) {
        adminCompetitionService.deleteCompetition(id);
        return ResponseEntity.noContent().build();
    }
}
