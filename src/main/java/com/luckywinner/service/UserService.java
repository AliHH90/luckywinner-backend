package com.luckywinner.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.luckywinner.dto.ChangePasswordRequest;
import com.luckywinner.dto.UpdateProfileRequest;
import com.luckywinner.dto.UserProfileResponse;
import com.luckywinner.entity.User;
import com.luckywinner.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    // گرفتن کاربر لاگین شده از SecurityContext
    private User getCurrentUserEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("No authenticated user");
        }

        String phone = auth.getName(); // در JWT: subject = phone
        User user = userRepository.findByPhone(phone);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // هر بار که کاربر درخواست می‌دهد، lastActiveAt را آپدیت می‌کنیم
        user.setLastActiveAt(LocalDateTime.now());
        userRepository.save(user);

        return user;
    }

 // تبدیل Entity به DTO
    private UserProfileResponse mapToProfileDto(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getPhone(),
                user.getFullName(),
                user.getRole(),
                user.getStatus(),
                user.getBalance(),
                user.getTotalWon(),
                user.getCreatedAt()
        );
    }



    // GET /api/me
    public UserProfileResponse getMyProfile() {
        User user = getCurrentUserEntity();
        return mapToProfileDto(user);
    }

    // PUT /api/me/profile
    public UserProfileResponse updateProfile(UpdateProfileRequest request) {
        User user = getCurrentUserEntity();

        if (request.getFullName() != null && !request.getFullName().isBlank()) {
            user.setFullName(request.getFullName());
        }

        userRepository.save(user);
        return mapToProfileDto(user);
    }

    // PUT /api/me/password
    public void changePassword(ChangePasswordRequest request) {
        User user = getCurrentUserEntity();

        if (!encoder.matches(request.getCurrentPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        String encoded = encoder.encode(request.getNewPassword());
        user.setPasswordHash(encoded);
        userRepository.save(user);
    }
}
