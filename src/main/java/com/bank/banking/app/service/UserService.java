package com.bank.banking.app.service;

import com.bank.banking.app.model.Transaction;
import com.bank.banking.app.model.User;
import com.bank.banking.app.repository.TransactionRepository;
import com.bank.banking.app.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public double getBalance(String username) {
        return getUserOrThrow(username).getBalance();
    }

    public void deposit(String username, double amount) {
        if (amount <= 0) throw new RuntimeException("Amount must be greater than 0");

        User user = getUserOrThrow(username);

        user.setBalance(user.getBalance() + amount);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);
        userRepository.save(user);
    }

    public void withdraw(String username, double amount) {
        if (amount <= 0) throw new RuntimeException("Amount must be greater than 0");

        User user = getUserOrThrow(username);

        if (user.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        user.setBalance(user.getBalance() - amount);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setType("WITHDRAW");
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);
        userRepository.save(user);
    }

    @Transactional
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

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        Transaction sent = new Transaction();
        sent.setUser(sender);
        sent.setType("TRANSFER_SENT");
        sent.setAmount(amount);
        sent.setTimestamp(LocalDateTime.now());
        sent.setSender(senderUsername);
        sent.setReceiver(receiverUsername);

        Transaction received = new Transaction();
        received.setUser(receiver);
        received.setType("TRANSFER_RECEIVED");
        received.setAmount(amount);
        received.setTimestamp(LocalDateTime.now());
        received.setSender(senderUsername);
        received.setReceiver(receiverUsername);

        transactionRepository.save(sent);
        transactionRepository.save(received);

        userRepository.save(sender);
        userRepository.save(receiver);
    }

    public List<Transaction> getTransactions(String username) {
        User user = getUserOrThrow(username);
        return transactionRepository.findByUser(user);
    }

    // ✅ FIXED CORE METHOD
    private User getUserOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}