package com.bank.banking.app.service;

import com.bank.banking.app.model.User;
import com.bank.banking.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    // 🧠 In-memory transaction storage (temporary)
    private final Map<String, List<String>> transactions = new HashMap<>();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 🔍 Get user
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (user.getBalance() == null) {
            user.setBalance(0.0);
        }

        return user;
    }

    // 💰 Deposit
    public String depositByUsername(String username, Double amount) {

        User user = getUserByUsername(username);

        if (amount == null || amount <= 0) {
            throw new RuntimeException("Invalid deposit amount");
        }

        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);

        // 📊 Save transaction
        transactions
                .computeIfAbsent(username, k -> new ArrayList<>())
                .add("Deposited ₹" + amount);

        return "Deposit successful";
    }

    // 💸 Withdraw
    public String withdrawByUsername(String username, Double amount) {

        User user = getUserByUsername(username);

        if (amount == null || amount <= 0) {
            throw new RuntimeException("Invalid withdraw amount");
        }

        if (user.getBalance() - amount < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);

        // 📊 Save transaction
        transactions
                .computeIfAbsent(username, k -> new ArrayList<>())
                .add("Withdrew ₹" + amount);

        return "Withdraw successful";
    }

    // 📊 Get transactions
    public List<String> getTransactions(String username) {
        return transactions.getOrDefault(username, new ArrayList<>());
    }
}