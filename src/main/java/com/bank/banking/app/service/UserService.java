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
    // ✅ DEPOSIT
    // ===============================
    public String deposit(String username, Double amount) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (amount <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        // ✅ update balance
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);

        // ✅ save transaction (FIXED)
        Transaction tx = new Transaction(user, "DEPOSIT", amount);
        tx.setTimestamp(LocalDateTime.now());

        transactionRepository.save(tx);

        return "Deposit successful";
    }

    // ===============================
    // ✅ WITHDRAW
    // ===============================
    public String withdraw(String username, Double amount) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (amount <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        if (user.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        // ✅ update balance
        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);

        // ✅ save transaction (FIXED)
        Transaction tx = new Transaction(user, "WITHDRAW", amount);
        tx.setTimestamp(LocalDateTime.now());

        transactionRepository.save(tx);

        return "Withdraw successful";
    }

    // ===============================
    // ✅ GET BALANCE
    // ===============================
    public Double getBalance(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return user.getBalance();
    }

    // ===============================
    // ✅ GET TRANSACTIONS
    // ===============================
    public List<Transaction> getTransactions(String username) {

        return transactionRepository.findByUserUsername(username);
    }
}