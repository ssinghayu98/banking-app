package com.bank.banking.app.service;

import com.bank.banking.app.model.User;
import com.bank.banking.app.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 🔍 Get user
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // ✅ Ensure balance is never null
        if (user.getBalance() == null) {
            user.setBalance(0.0);
        }

        return user;
    }

    // 💰 Deposit
    public String depositByUsername(String username, Double amount) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // 🚨 Validate amount
        if (amount == null || amount <= 0) {
            throw new RuntimeException("Invalid deposit amount");
        }

        if (user.getBalance() == null) {
            user.setBalance(0.0);
        }

        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);

        return "Deposit successful";
    }

    // 💸 Withdraw
    public String withdrawByUsername(String username, Double amount) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // 🚨 Validate amount
        if (amount == null || amount <= 0) {
            throw new RuntimeException("Invalid withdraw amount");
        }

        if (user.getBalance() == null) {
            user.setBalance(0.0);
        }

        // 🚨 STRICT RULE: no negative balance
        if (user.getBalance() - amount < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);

        return "Withdraw successful";
    }
}