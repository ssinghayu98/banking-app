package com.bank.banking.app.controller;

import com.bank.banking.app.dto.ApiResponse;
import com.bank.banking.app.dto.LoginRequest;
import com.bank.banking.app.dto.UserResponseDto;
import com.bank.banking.app.model.User;
import com.bank.banking.app.service.AuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> register(@RequestBody LoginRequest request) {
        try {
            validate(request);

            User user = authService.register(
                    request.getUsername().trim(),
                    request.getPassword()
            );

            return ResponseEntity.ok(new ApiResponse<>("Registration successful",
                    mapToDto(user)));

        } catch (Exception e) {
            logger.error("Registration failed", e);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDto>> login(@RequestBody LoginRequest request) {
        try {
            validate(request);

            User user = authService.login(
                    request.getUsername().trim(),
                    request.getPassword()
            );

            return ResponseEntity.ok(new ApiResponse<>("Login successful",
                    mapToDto(user)));

        } catch (Exception e) {
            logger.error("Login failed", e);
            return ResponseEntity.status(401)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }

    private void validate(LoginRequest req) {
        if (req.getUsername() == null || req.getPassword() == null) {
            throw new RuntimeException("Username and password required");
        }
    }

    private UserResponseDto mapToDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getBalance(),
                user.getRole()
        );
    }
}