package com.bank.banking.app.service;

import com.bank.banking.app.model.User;
import com.bank.banking.app.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ===============================
    // 📝 REGISTER
    // ===============================
    public User register(String username, String password) {

        // 🔍 Validation
        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("Username is required");
        }

        if (password == null || password.trim().length() < 4) {
            throw new RuntimeException("Password must be at least 4 characters");
        }

        // 🔥 Normalize username
        username = username.trim().toLowerCase();

        // 🔍 Check existing user
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }

        // 🔐 Hash password
        String hashedPassword = passwordEncoder.encode(password);

        // 👤 Create user
        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);

        // 🔥 REQUIRED FIELDS (CRITICAL)
        user.setBalance(0.0);
        user.setRole("USER");

        // 💾 Save user
        User savedUser = userRepository.save(user);

        System.out.println("✅ USER REGISTERED: " + username);

        return savedUser;
    }

    // ===============================
    // 🔐 LOGIN
    // ===============================
    public User login(String username, String password) {

        if (username == null || password == null) {
            throw new RuntimeException("Username and password required");
        }

        username = username.trim().toLowerCase();

        User user = userRepository.findByUsername(username);

        if (user == null) {
            System.out.println("❌ USER NOT FOUND: " + username);
            throw new RuntimeException("Invalid username or password");
        }

        // 🔐 Password match
        if (!passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("❌ WRONG PASSWORD for: " + username);
            throw new RuntimeException("Invalid username or password");
        }

        System.out.println("✅ LOGIN SUCCESS: " + username);

        return user;
    }
}