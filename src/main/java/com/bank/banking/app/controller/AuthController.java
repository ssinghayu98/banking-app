package com.bank.banking.app.controller;

import com.bank.banking.app.dto.ApiResponse;
import com.bank.banking.app.dto.LoginRequest;
import com.bank.banking.app.model.User;
import com.bank.banking.app.service.AuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") // allow all origins (ok for now, tighten later)
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

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
            // 🔍 Validation
            if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(
                        new ApiResponse<>("Username is required", null)
                );
            }

            if (request.getPassword() == null || request.getPassword().length() < 4) {
                return ResponseEntity.badRequest().body(
                        new ApiResponse<>("Password must be at least 4 characters", null)
                );
            }

            User user = authService.register(
                    request.getUsername().trim(),
                    request.getPassword()
            );

            return ResponseEntity.ok(
                    new ApiResponse<>("Registration successful", user)
            );

        } catch (Exception e) {

            // 🔥 PROPER LOGGING (better than printStackTrace)
            logger.error("Registration failed", e);

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
                    request.getUsername().trim(),
                    request.getPassword()
            );

            return ResponseEntity.ok(
                    new ApiResponse<>("Login successful", user)
            );

        } catch (Exception e) {

            logger.error("Login failed", e);

            return ResponseEntity.status(401).body(
                    new ApiResponse<>(e.getMessage(), null)
            );
        }
    }
}