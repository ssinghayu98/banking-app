package com.bank.banking.app.service;

import com.bank.banking.app.model.User;
import com.bank.banking.app.repository.UserRepository;
import com.bank.banking.app.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       JwtUtil jwtUtil,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    // ===============================
    // 🔐 LOGIN (WITH FULL DEBUG)
    // ===============================
    public String login(String username, String password) {

        System.out.println("======== LOGIN DEBUG START ========");
        System.out.println("INPUT USERNAME: " + username);
        System.out.println("INPUT PASSWORD: " + password);

        // 🔍 Find user
        User user = userRepository.findByUsername(username);

        if (user == null) {
            System.out.println("❌ USER NOT FOUND IN DATABASE");
            throw new RuntimeException("User not found");
        }

        System.out.println("✅ USER FOUND: " + user.getUsername());
        System.out.println("DB PASSWORD (HASH): " + user.getPassword());

        // 🔑 Compare encrypted password
        boolean match = passwordEncoder.matches(password, user.getPassword());

        System.out.println("PASSWORD MATCH RESULT: " + match);

        if (!match) {
            System.out.println("❌ PASSWORD DOES NOT MATCH");
            throw new RuntimeException("Invalid username or password");
        }

        System.out.println("✅ PASSWORD MATCH SUCCESS");

        // 🎟 Generate JWT token
        String token = jwtUtil.generateToken(username);

        System.out.println("✅ TOKEN GENERATED: " + token);
        System.out.println("======== LOGIN DEBUG END ========");

        return token;
    }

    // ===============================
    // 📝 REGISTER
    // ===============================
    public String register(String username, String password) {

        System.out.println("======== REGISTER DEBUG ========");

        if (userRepository.findByUsername(username) != null) {
            System.out.println("❌ USER ALREADY EXISTS");
            throw new RuntimeException("Username already exists");
        }

        // 🔐 Encrypt password
        String encodedPassword = passwordEncoder.encode(password);
        System.out.println("ENCODED PASSWORD: " + encodedPassword);

        // 👤 Create user
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setBalance(0.0);
        user.setRole("USER");

        userRepository.save(user);

        System.out.println("✅ USER REGISTERED SUCCESSFULLY");
        System.out.println("======== REGISTER END ========");

        return "User registered successfully";
    }
}