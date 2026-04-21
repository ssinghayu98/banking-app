package com.bank.banking.app.controller;

import com.bank.banking.app.dto.LoginRequest;
import com.bank.banking.app.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 🔐 LOGIN
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {

        String token = authService.login(
                request.getUsername(),
                request.getPassword()
        );

        return Map.of("token", token);
    }

    // 📝 REGISTER
    @PostMapping("/register")
    public String register(@RequestBody LoginRequest request) {

        authService.register(
                request.getUsername(),
                request.getPassword()
        );

        return "User registered successfully";
    }
}