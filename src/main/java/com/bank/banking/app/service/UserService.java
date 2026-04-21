package com.bank.banking.app.service;

import com.bank.banking.app.model.User;
import com.bank.banking.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor Injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ Get user by username (FIXED - REAL DB FETCH)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // ✅ Deposit (basic version)
    public String deposit(Long userId, Double amount) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        User user = optionalUser.get();
        user.setBalance(user.getBalance() + amount);

        userRepository.save(user);

        return "Amount deposited successfully";
    }

    // ✅ Withdraw (basic version)
    public String withdraw(Long userId, Double amount) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        User user = optionalUser.get();

        if (user.getBalance() < amount) {
            return "Insufficient balance";
        }

        user.setBalance(user.getBalance() - amount);

        userRepository.save(user);

        return "Amount withdrawn successfully";
    }

    // ✅ Transfer (basic version)
    public String transferMoney(Long senderId, Long receiverId, Double amount) {

        Optional<User> senderOpt = userRepository.findById(senderId);
        Optional<User> receiverOpt = userRepository.findById(receiverId);

        if (senderOpt.isEmpty() || receiverOpt.isEmpty()) {
            return "User not found";
        }

        User sender = senderOpt.get();
        User receiver = receiverOpt.get();

        if (sender.getBalance() < amount) {
            return "Insufficient balance";
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        userRepository.save(sender);
        userRepository.save(receiver);

        return "Money transferred successfully";
    }

    // ✅ Dummy transactions (we will upgrade later)
    public List<String> getTransactionsByUserId(Long userId) {
        return Arrays.asList("TXN1", "TXN2");
    }
}