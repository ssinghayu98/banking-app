package com.bank.banking.app.controller;

import com.bank.banking.app.dto.ApiResponse;
import com.bank.banking.app.dto.LoginRequest;
import com.bank.banking.app.model.User;
import com.bank.banking.app.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ===============================
    // 📝 REGISTER
    // ===============================
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody LoginRequest request) {

        try {
            // 🔍 Basic validation
            if (request.getUsername() == null || request.getPassword() == null) {
                return ResponseEntity.badRequest().body(
                        new ApiResponse<>("Username and password required", null)
                );
            }

            User user = authService.register(
                    request.getUsername(),
                    request.getPassword()
            );

            return ResponseEntity.ok(
                    new ApiResponse<>("Registration successful", user)
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(e.getMessage(), null)
            );
        }
    }

    // ===============================
    // 🔐 LOGIN
    // ===============================
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> login(@RequestBody LoginRequest request) {

        try {
            if (request.getUsername() == null || request.getPassword() == null) {
                return ResponseEntity.badRequest().body(
                        new ApiResponse<>("Username and password required", null)
                );
            }

            User user = authService.login(
                    request.getUsername(),
                    request.getPassword()
            );

            return ResponseEntity.ok(
                    new ApiResponse<>("Login successful", user)
            );

        } catch (Exception e) {
            return ResponseEntity.status(401).body(
                    new ApiResponse<>(e.getMessage(), null)
            );
        }
    }
}