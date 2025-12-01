package com.luckywinner.controller;

import com.luckywinner.dto.ChangePasswordRequest;
import com.luckywinner.dto.UpdateProfileRequest;
import com.luckywinner.dto.UserProfileResponse;
import com.luckywinner.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/me")
@CrossOrigin(origins = "*")
public class MeController {

    private final UserService userService;

    public MeController(UserService userService) {
        this.userService = userService;
    }

    // GET /api/me  -> برگرداندن پروفایل خود کاربر
    @GetMapping
    public ResponseEntity<UserProfileResponse> me() {
        UserProfileResponse profile = userService.getMyProfile();
        return ResponseEntity.ok(profile);
    }

    // PUT /api/me/profile  -> تغییر نام
    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(
            @RequestBody UpdateProfileRequest request) {

        UserProfileResponse updated = userService.updateProfile(request);
        return ResponseEntity.ok(updated);
    }

    // PUT /api/me/password -> تغییر رمز
    @PutMapping("/password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request) {

        try {
            userService.changePassword(request);
            return ResponseEntity.ok("Password changed successfully");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
