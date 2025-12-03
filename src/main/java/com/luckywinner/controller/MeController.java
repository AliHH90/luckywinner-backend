package com.luckywinner.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckywinner.dto.ChangePasswordRequest;
import com.luckywinner.dto.UpdateProfileRequest;
import com.luckywinner.dto.UserProfileResponse;
import com.luckywinner.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/me")
@CrossOrigin(origins = "*")
public class MeController {

    private final UserService userService;

    public MeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserProfileResponse> me() {
        UserProfileResponse profile = userService.getMyProfile();
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(
            @RequestBody UpdateProfileRequest request) {

        UserProfileResponse updated = userService.updateProfile(request);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request) {

        try {
            userService.changePassword(request);
            // همیشه JSON برگردانیم
            return ResponseEntity.ok(Map.of("message", "رمز عبور با موفقیت تغییر کرد."));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", ex.getMessage()));
        }
    }
}

