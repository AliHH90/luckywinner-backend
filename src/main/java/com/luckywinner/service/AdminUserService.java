// src/main/java/com/luckywinner/service/AdminUserService.java
package com.luckywinner.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.luckywinner.dto.AdminUserResponse;
import com.luckywinner.entity.User;
import com.luckywinner.repository.UserRepository;

@Service
public class AdminUserService {

    private final UserRepository userRepository;
    private final UserService userService; // 👈 اضافه شد

    public AdminUserService(UserRepository userRepository,
                            UserService userService) { // 👈 سازنده اصلاح شد
        this.userRepository = userRepository;
        this.userService = userService;
    }

    // لیست همه کاربران برای پنل ادمین
    public List<AdminUserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private AdminUserResponse toDto(User u) {
        String roleName = u.getRole();  // "ADMIN" یا "USER"

        return new AdminUserResponse(
                u.getId(),
                u.getPhone(),
                u.getFullName(),
                u.getBalance(),
                u.getTotalWon(),
                roleName,
                u.getCreatedAt(),
                u.getLastActiveAt()
        );
    }

    // ✅ بلاک / آن‌بلاک کردن کاربر (می‌فرستیم به UserService)
    public void setUserBlocked(Long userId, boolean blocked) {
        userService.setUserBlocked(userId, blocked);
    }

    // ✅ حذف کاربر (با پاک کردن participationها و تراکنش‌ها)
    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }
}
