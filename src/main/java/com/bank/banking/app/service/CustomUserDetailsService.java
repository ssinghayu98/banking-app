package com.bank.banking.app.service;

import com.bank.banking.app.model.User;
import com.bank.banking.app.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("🔍 Loading user: " + username);

        User user = userRepository.findByUsername(username);

        if (user == null) {
            System.out.println("❌ User NOT found");
            throw new UsernameNotFoundException("User not found");
        }

        System.out.println("✅ User found: " + user.getUsername());

        // 🔥 FINAL FIX → NO ROLES (prevents 403 issues)
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>() // ✅ empty authorities = safe for authenticated()
        );
    }
}