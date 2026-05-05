package com.bank.banking.app.service;

import com.bank.banking.app.model.User;
import com.bank.banking.app.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
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

        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        String hashedPassword = passwordEncoder.encode(password);

        System.out.println("🔐 REGISTER HASH: " + hashedPassword);

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setBalance(0.0);
        user.setRole("USER");

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

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        System.out.println("👤 LOGIN USER: " + username);
        System.out.println("🔑 RAW PASSWORD: " + password);
        System.out.println("🔐 DB HASH: " + user.getPassword());

        boolean match = passwordEncoder.matches(password, user.getPassword());

        System.out.println("✅ PASSWORD MATCH: " + match);

        if (!match) {
            throw new RuntimeException("Invalid username or password");
        }

        return user;
    }
}