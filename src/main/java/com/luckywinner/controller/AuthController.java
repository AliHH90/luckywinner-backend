package com.luckywinner.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckywinner.dto.AuthResponse;
import com.luckywinner.dto.LoginRequest;
import com.luckywinner.dto.RegisterRequest;
import com.luckywinner.entity.User;
import com.luckywinner.repository.UserRepository;
import com.luckywinner.security.jwt.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserRepository userRepository,
                          BCryptPasswordEncoder encoder,
                          JwtUtil jwtUtil,
                          AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    // ------------ ایجاد ادمین جدید (نسخه ساده و بدون AuthService) ------------
    // توجه: به‌خاطر تنظیم SecurityConfig فعلی، این endpoint الان برای همه باز است.
    // بعد از ساخت اولین ادمین، می‌توانیم آن را محدود کنیم.
    @PostMapping("/admin/create")
    public ResponseEntity<AuthResponse> createAdmin(@RequestBody RegisterRequest request) {

        // اگر کاربر با این شماره از قبل وجود داشته باشد
        User existing = userRepository.findByPhone(request.getPhone());
        if (existing != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new AuthResponse(
                            null,
                            request.getPhone(),
                            null,
                            "PHONE_ALREADY_EXISTS"
                    ));
        }

        User user = new User();
        user.setPhone(request.getPhone());
        user.setFullName(request.getFullName());
        user.setPasswordHash(encoder.encode(request.getPassword()));
        user.setRole("ADMIN");              // 👈 اینجا ادمین می‌سازیم
        user.setBalance(0.0);
        user.setTotalWon(0.0);
        user.setCreatedAt(LocalDateTime.now());
        user.setLastActiveAt(LocalDateTime.now());

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getPhone(), user.getRole());

        AuthResponse response = new AuthResponse(
                token,
                user.getPhone(),
                user.getFullName(),
                user.getRole()
        );

        return ResponseEntity.ok(response);
    }

    // ------------ REGISTER معمولی (کاربر عادی) ------------
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {

        // اگر کاربر با این شماره وجود دارد
        User existing = userRepository.findByPhone(request.getPhone());
        if (existing != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new AuthResponse(
                            null,
                            request.getPhone(),
                            null,
                            "PHONE_ALREADY_EXISTS"
                    ));
        }

        User user = new User();
        user.setPhone(request.getPhone());
        user.setFullName(request.getFullName());
        user.setPasswordHash(encoder.encode(request.getPassword()));
        user.setRole("USER");
        user.setBalance(0.0);
        user.setTotalWon(0.0);
        user.setCreatedAt(LocalDateTime.now());
        user.setLastActiveAt(LocalDateTime.now());

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getPhone(), user.getRole());

        AuthResponse response = new AuthResponse(
                token,
                user.getPhone(),
                user.getFullName(),
                user.getRole()
        );

        return ResponseEntity.ok(response);
    }

    // ------------- LOGIN --------------
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPhone(),
                        request.getPassword()
                )
        );

        // اگر authenticate موفق باشد:
        User user = userRepository.findByPhone(request.getPhone());

        String token = jwtUtil.generateToken(user.getPhone(), user.getRole());

        AuthResponse response = new AuthResponse(
                token,
                user.getPhone(),
                user.getFullName(),
                user.getRole()
        );

        return ResponseEntity.ok(response);
    }
}
