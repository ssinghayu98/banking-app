package com.bank.banking.app.service;

import com.bank.banking.app.model.User;
import com.bank.banking.app.repository.UserRepository;
import com.bank.banking.app.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String login(String username, String password) {

        // 🔍 Find user from DB
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // 🔥 IMPORTANT: simple password check (no encryption for now)
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        // 🔐 Generate JWT token
        String token = jwtUtil.generateToken(username);

        return token; // ✅ MUST RETURN TOKEN
    }
}