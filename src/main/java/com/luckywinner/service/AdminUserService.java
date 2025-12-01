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

    public AdminUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // لیست همه کاربران برای پنل ادمین
    public List<AdminUserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private AdminUserResponse toDto(User u) {

        // چون role در User از نوع String است:
        String roleName = u.getRole();   // ✅ فقط همین

        return new AdminUserResponse(
                u.getId(),
                u.getPhone(),
                u.getFullName(),
                u.getBalance(),       // اگر این فیلد در User نداری، این خط و پارامترش را از DTO حذف کن
                u.getTotalWon(),      // همین‌طور این
                roleName,
                u.getCreatedAt(),     // اگر نداری → null بگذار یا حذفش کن
                u.getLastActiveAt()   // اگر نداری → null بگذار یا حذفش کن
        );
    }
}
