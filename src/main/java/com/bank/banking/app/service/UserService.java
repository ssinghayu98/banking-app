package com.bank.banking.app.service;

import com.bank.banking.app.model.Transaction;
import com.bank.banking.app.model.User;
import com.bank.banking.app.repository.TransactionRepository;
import com.bank.banking.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public UserService(UserRepository userRepository,
                       TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    // ===============================
    // 💰 GET BALANCE
    // ===============================
    public double getBalance(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        System.out.println("💰 Balance fetched for: " + username);

        return user.getBalance();
    }

    // ===============================
    // ➕ DEPOSIT
    // ===============================
    public void deposit(String username, double amount) {

        System.out.println("➕ DEPOSIT CALLED: " + username + " | Amount: " + amount);

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        // Update balance
        user.setBalance(user.getBalance() + amount);

        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setUser(user);
        transaction.setTimestamp(LocalDateTime.now()); // ✅ IMPORTANT

        // Save
        transactionRepository.save(transaction);
        userRepository.save(user);

        System.out.println("✅ DEPOSIT SUCCESS");
    }

    // ===============================
    // ➖ WITHDRAW
    // ===============================
    public void withdraw(String username, double amount) {

        System.out.println("➖ WITHDRAW CALLED: " + username + " | Amount: " + amount);

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        if (user.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        // Update balance
        user.setBalance(user.getBalance() - amount);

        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setType("WITHDRAW");
        transaction.setAmount(amount);
        transaction.setUser(user);
        transaction.setTimestamp(LocalDateTime.now()); // ✅ IMPORTANT

        // Save
        transactionRepository.save(transaction);
        userRepository.save(user);

        System.out.println("✅ WITHDRAW SUCCESS");
    }

    // ===============================
    // 📜 GET TRANSACTIONS
    // ===============================
    public List<Transaction> getTransactions(String username) {

        System.out.println("📜 FETCHING TRANSACTIONS FOR: " + username);

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        List<Transaction> transactions = transactionRepository.findByUser(user);

        System.out.println("🔥 Transactions found: " + transactions.size());

        return transactions;
    }
}