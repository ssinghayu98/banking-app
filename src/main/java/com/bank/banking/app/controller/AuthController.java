package com.bank.banking.app.controller;

import com.bank.banking.app.dto.LoginRequest;
import com.bank.banking.app.dto.ApiResponse;
import com.bank.banking.app.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ===============================
    // 📝 REGISTER
    // ===============================
    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody LoginRequest request) {

        try {
            authService.register(request.getUsername(), request.getPassword());
            return new ApiResponse<>("Registration successful", null);

        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null);
        }
    }

    // ===============================
    // 🔐 LOGIN (FIXED)
    // ===============================
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody LoginRequest request) {

        try {
            // ✅ Validate user
            authService.login(request.getUsername(), request.getPassword());

            // ✅ Only return success if valid
            return new ApiResponse<>("Login successful", null);

        } catch (Exception e) {
            return new ApiResponse<>(e.getMessage(), null);
        }
    }
}