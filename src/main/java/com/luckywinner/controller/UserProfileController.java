package com.luckywinner.controller;

import com.luckywinner.dto.UpdateFullNameRequest;
import com.luckywinner.dto.UpdatePasswordRequest;
import com.luckywinner.entity.User;
import com.luckywinner.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/user/profile")
public class UserProfileController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserProfileController(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // کمک‌کننده: گرفتن کاربر لاگین‌شده
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("No authenticated user");
        }

        String phone = auth.getName(); // ما توی JWT، phone رو به عنوان username گذاشتیم
        User user = userRepository.findByPhone(phone);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        user.setLastActiveAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    // --------- تغییر رمز عبور ---------
    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody UpdatePasswordRequest request) {

        if (request.getOldPassword() == null || request.getNewPassword() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "رمز فعلی و رمز جدید الزامی است."));
        }

        User user = getCurrentUser();

        // بررسی صحت رمز فعلی
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPasswordHash())) {
            return ResponseEntity.status(403)
                    .body(Map.of("message", "رمز فعلی نادرست است."));
        }

        // می‌توانی اینجا چک‌های بیشتر (حداقل طول، پیچیدگی و...) بگذاری
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "رمز عبور با موفقیت تغییر کرد."));
    }

    // --------- تغییر نام کاربر ---------
    @PutMapping("/name")
    public ResponseEntity<?> changeFullName(@RequestBody UpdateFullNameRequest request) {

        if (request.getFullName() == null || request.getFullName().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "نام کامل نمی‌تواند خالی باشد."));
        }

        User user = getCurrentUser();
        user.setFullName(request.getFullName().trim());
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "نام کاربر با موفقیت به‌روزرسانی شد."));
    }
}
