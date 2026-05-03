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

        try {
            if (username == null || username.trim().isEmpty()) {
                throw new RuntimeException("Username is required");
            }

            if (password == null || password.trim().length() < 4) {
                throw new RuntimeException("Password must be at least 4 characters");
            }

            username = username.trim().toLowerCase();

            if (userRepository.findByUsername(username) != null) {
                throw new RuntimeException("Username already exists");
            }

            String hashedPassword = passwordEncoder.encode(password);

            User user = new User();
            user.setUsername(username);
            user.setPassword(hashedPassword);
            user.setBalance(0.0);
            user.setRole("USER");

            return userRepository.save(user);

        } catch (Exception e) {
            e.printStackTrace(); // shows in Railway logs
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
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
            throw new RuntimeException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return user;
    }
}