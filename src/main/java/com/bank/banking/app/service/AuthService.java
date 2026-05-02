package com.bank.banking.app.service;

import com.bank.banking.app.model.User;
import com.bank.banking.app.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    // 🔥 Password encoder
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ===============================
    // 📝 REGISTER
    // ===============================
    public String register(String username, String password) {

        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("Username already exists");
        }

        // 🔥 HASH PASSWORD
        String hashedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setBalance(0.0);

        userRepository.save(user);

        System.out.println("✅ USER REGISTERED: " + username);

        return "Registration successful";
    }

    // ===============================
    // 🔐 LOGIN (FINAL FIX)
    // ===============================
    public User login(String username, String password) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            System.out.println("❌ USER NOT FOUND");
            throw new RuntimeException("Invalid username or password");
        }

        System.out.println("✅ USER FOUND: " + username);

        // 🔥 PASSWORD CHECK
        boolean isMatch = passwordEncoder.matches(password, user.getPassword());

        if (!isMatch) {
            System.out.println("❌ PASSWORD DOES NOT MATCH");
            throw new RuntimeException("Invalid username or password");
        }

        System.out.println("✅ LOGIN SUCCESS");

        // 🔥 RETURN USER (CRITICAL FIX)
        return user;
    }
}