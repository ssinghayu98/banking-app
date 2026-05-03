package com.bank.banking.app.service;

import com.bank.banking.app.model.User;
import com.bank.banking.app.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("Username is required");
        }

        if (password == null || password.trim().length() < 4) {
            throw new RuntimeException("Password must be at least 4 characters");
        }

        username = username.trim().toLowerCase();

        // ✅ FIXED: Proper Optional handling
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // ✅ Hash password
        String hashedPassword = passwordEncoder.encode(password);

        // ✅ Create user
        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setBalance(0.0);
        user.setRole("USER");

        // ✅ SAVE TO DB (CRITICAL)
        return userRepository.save(user);
    }

    // ===============================
    // 🔐 LOGIN
    // ===============================
    public User login(String username, String password) {

        if (username == null || password == null) {
            throw new RuntimeException("Username and password required");
        }

        username = username.trim().toLowerCase();

        // ✅ FIXED: Optional
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Invalid username or password");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return user;
    }
}