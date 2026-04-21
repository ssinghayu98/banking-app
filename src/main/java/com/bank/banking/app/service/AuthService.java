package com.bank.banking.app.service;

import com.bank.banking.app.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    // 🔐 LOGIN METHOD
    public String login(String username, String password) {

        System.out.println("AUTH SERVICE CALLED: " + username);

        // ✅ Spring Security handles password check
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // ✅ Generate JWT token
        return jwtUtil.generateToken(username);
    }
}