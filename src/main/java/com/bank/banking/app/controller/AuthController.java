package com.bank.banking.app.controller;

import com.bank.banking.app.dto.LoginRequest;
import com.bank.banking.app.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") // 🔥 VERY IMPORTANT for React
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 🔐 LOGIN API
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {

        String token = authService.login(
                request.getUsername(),
                request.getPassword()
        );

        // ✅ RETURN JSON (NOT STRING)
        return Map.of("token", token);
    }
}