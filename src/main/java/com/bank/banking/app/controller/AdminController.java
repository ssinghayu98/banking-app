package com.bank.banking.app.controller;

import com.bank.banking.app.dto.ApiResponse;
import com.bank.banking.app.model.Transaction;
import com.bank.banking.app.model.User;
import com.bank.banking.app.repository.TransactionRepository;
import com.bank.banking.app.repository.UserRepository;
import com.bank.banking.app.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public AdminController(UserService userService,
                           UserRepository userRepository,
                           TransactionRepository transactionRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    // ===============================
    // 👥 GET ALL USERS
    // ===============================
    @GetMapping("/users")
    public ApiResponse<List<User>> getAllUsers() {

        List<User> users = userRepository.findAll();

        return new ApiResponse<>("Users fetched successfully", users);
    }

    // ===============================
    // 💰 GET ALL TRANSACTIONS (SORTED)
    // ===============================
    @GetMapping("/transactions")
    public ApiResponse<List<Transaction>> getAllTransactions() {

        List<Transaction> transactions = transactionRepository.findAll();

        // 🔥 Sort latest first
        transactions.sort(Comparator.comparing(Transaction::getTimestamp).reversed());

        return new ApiResponse<>("Transactions fetched successfully", transactions);
    }

    // ===============================
    // ➕ ADMIN DEPOSIT
    // ===============================
    @PostMapping("/deposit")
    public ApiResponse<String> deposit(@RequestParam String username,
                                       @RequestParam Double amount) {

        if (amount == null || amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        userService.deposit(username, amount);

        return new ApiResponse<>("Admin deposit successful", null);
    }

    // ===============================
    // ➖ ADMIN WITHDRAW
    // ===============================
    @PostMapping("/withdraw")
    public ApiResponse<String> withdraw(@RequestParam String username,
                                        @RequestParam Double amount) {

        if (amount == null || amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        userService.withdraw(username, amount);

        return new ApiResponse<>("Admin withdraw successful", null);
    }

    // ===============================
    // ❌ DELETE USER
    // ===============================
    @DeleteMapping("/user")
    public ApiResponse<String> deleteUser(@RequestParam String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        userRepository.delete(user);

        return new ApiResponse<>("User deleted successfully", null);
    }
}