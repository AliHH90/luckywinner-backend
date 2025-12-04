// src/main/java/com/luckywinner/controller/AdminUserController.java
package com.luckywinner.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckywinner.dto.AdminUserResponse;
import com.luckywinner.service.AdminUserService;

@RestController
@RequestMapping("/api/admin/users")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping
    public ResponseEntity<List<AdminUserResponse>> getAllUsers() {
        List<AdminUserResponse> users = adminUserService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<Void> setUserBlocked(@PathVariable Long id,
                                               @RequestBody BlockUserRequest request) {
        adminUserService.setUserBlocked(id, request.isBlocked());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // DTO کوچک برای درخواست بلاک/آنبلاک
    public static class BlockUserRequest {
        private boolean blocked;

        public boolean isBlocked() {
            return blocked;
        }

        public void setBlocked(boolean blocked) {
            this.blocked = blocked;
        }
    }
}
