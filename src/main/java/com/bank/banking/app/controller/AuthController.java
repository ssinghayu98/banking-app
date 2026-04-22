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
            String message = authService.register(request.getUsername(), request.getPassword());
            return ApiResponse.success(message);

        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    // ===============================
    // 🔐 LOGIN
    // ===============================
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody LoginRequest request) {

        try {
            String token = authService.login(request.getUsername(), request.getPassword());
            return ApiResponse.success(token);

        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}