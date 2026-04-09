package com.bank.banking.app.service;

import com.bank.banking.app.dto.TransactionResponseDto;
import com.bank.banking.app.dto.UserResponseDto;
import com.bank.banking.app.exception.InsufficientBalanceException;
import com.bank.banking.app.exception.InvalidAmountException;
import com.bank.banking.app.exception.InvalidTransferException;
import com.bank.banking.app.exception.UserNotFoundException;
import com.bank.banking.app.exception.UsernameAlreadyExistsException;
import com.bank.banking.app.model.Role;
import com.bank.banking.app.model.Transaction;
import com.bank.banking.app.model.User;
import com.bank.banking.app.repository.TransactionRepository;
import com.bank.banking.app.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       TransactionRepository transactionRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto registerUser(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new RuntimeException("Username cannot be empty");
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Password cannot be empty");
        }

        String cleanedUsername = user.getUsername().trim();
        user.setUsername(cleanedUsername);

        if (userRepository.findByUsername(cleanedUsername).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getBalance() == null) {
            user.setBalance(0.0);
        }

        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);
        return mapToUserResponseDto(savedUser);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserResponseDto)
                .toList();
    }

    public String deposit(Long userId, Double amount) {
        if (amount == null || amount <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);

        Transaction transaction = new Transaction();
        transaction.setSenderId(0L);
        transaction.setReceiverId(userId);
        transaction.setAmount(amount);
        transaction.setType("DEPOSIT");

        transactionRepository.save(transaction);

        return "Amount deposited successfully";
    }

    public String withdraw(Long userId, Double amount) {
        if (amount == null || amount <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);

        Transaction transaction = new Transaction();
        transaction.setSenderId(userId);
        transaction.setReceiverId(0L);
        transaction.setAmount(amount);
        transaction.setType("WITHDRAW");

        transactionRepository.save(transaction);

        return "Amount withdrawn successfully";
    }

    public Double checkBalance(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return user.getBalance();
    }

    public String transferMoney(Long senderId, Long receiverId, Double amount) {
        if (amount == null || amount <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero");
        }

        if (senderId.equals(receiverId)) {
            throw new InvalidTransferException("Sender and receiver cannot be same");
        }

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new UserNotFoundException("Sender not found"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new UserNotFoundException("Receiver not found"));

        if (sender.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        userRepository.save(sender);
        userRepository.save(receiver);

        Transaction transaction = new Transaction();
        transaction.setSenderId(senderId);
        transaction.setReceiverId(receiverId);
        transaction.setAmount(amount);
        transaction.setType("TRANSFER");

        transactionRepository.save(transaction);

        return "Transfer successful";
    }

    public List<TransactionResponseDto> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(this::mapToTransactionResponseDto)
                .toList();
    }

    public String deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userRepository.delete(user);

        return "User deleted successfully";
    }

    private UserResponseDto mapToUserResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getBalance(),
                user.getRole()
        );
    }

    private TransactionResponseDto mapToTransactionResponseDto(Transaction transaction) {
        return new TransactionResponseDto(
                transaction.getId(),
                transaction.getSenderId(),
                transaction.getReceiverId(),
                transaction.getAmount(),
                transaction.getType()
        );
    }
}