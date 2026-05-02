package com.bank.banking.app.service;

import com.bank.banking.app.model.Transaction;
import com.bank.banking.app.model.User;
import com.bank.banking.app.repository.TransactionRepository;
import com.bank.banking.app.repository.UserRepository;
import org.springframework.stereotype.Service;

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

        User user = getUserOrThrow(username);
        return user.getBalance();
    }

    // ===============================
    // ➕ DEPOSIT
    // ===============================
    public void deposit(String username, double amount) {

        if (amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        User user = getUserOrThrow(username);

        user.setBalance(user.getBalance() + amount);

        Transaction transaction = new Transaction(
                user,
                "DEPOSIT",
                amount
        );

        transactionRepository.save(transaction);
        userRepository.save(user);
    }

    // ===============================
    // ➖ WITHDRAW
    // ===============================
    public void withdraw(String username, double amount) {

        if (amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        User user = getUserOrThrow(username);

        if (user.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        user.setBalance(user.getBalance() - amount);

        Transaction transaction = new Transaction(
                user,
                "WITHDRAW",
                amount
        );

        transactionRepository.save(transaction);
        userRepository.save(user);
    }

    // ===============================
    // 💸 TRANSFER (FINAL FIXED)
    // ===============================
    public void transfer(String senderUsername, String receiverUsername, double amount) {

        if (senderUsername.equals(receiverUsername)) {
            throw new RuntimeException("Cannot transfer to same user");
        }

        if (amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        User sender = getUserOrThrow(senderUsername);
        User receiver = getUserOrThrow(receiverUsername);

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        // 💰 Update balances
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        // 🔥 SAVE BOTH SIDES (IMPORTANT)

        // Sender view
        Transaction sent = new Transaction(
                sender,
                "TRANSFER",
                amount,
                senderUsername,
                receiverUsername
        );

        // Receiver view
        Transaction received = new Transaction(
                receiver,
                "TRANSFER",
                amount,
                senderUsername,
                receiverUsername
        );

        transactionRepository.save(sent);
        transactionRepository.save(received);

        userRepository.save(sender);
        userRepository.save(receiver);
    }

    // ===============================
    // 📜 GET TRANSACTIONS
    // ===============================
    public List<Transaction> getTransactions(String username) {

        User user = getUserOrThrow(username);
        return transactionRepository.findByUser(user);
    }

    // ===============================
    // 🔧 HELPER METHOD
    // ===============================
    private User getUserOrThrow(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return user;
    }
}