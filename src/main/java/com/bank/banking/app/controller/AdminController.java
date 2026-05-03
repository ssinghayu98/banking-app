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

    @GetMapping("/users")
    public ApiResponse<List<User>> getAllUsers() {
        return new ApiResponse<>("Users fetched successfully", userRepository.findAll());
    }

    @GetMapping("/transactions")
    public ApiResponse<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        transactions.sort(Comparator.comparing(Transaction::getTimestamp).reversed());
        return new ApiResponse<>("Transactions fetched successfully", transactions);
    }

    @PostMapping("/deposit")
    public ApiResponse<String> deposit(@RequestParam String username,
                                       @RequestParam Double amount) {

        if (amount == null || amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        userService.deposit(username, amount);
        return new ApiResponse<>("Admin deposit successful", null);
    }

    @PostMapping("/withdraw")
    public ApiResponse<String> withdraw(@RequestParam String username,
                                        @RequestParam Double amount) {

        if (amount == null || amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        userService.withdraw(username, amount);
        return new ApiResponse<>("Admin withdraw successful", null);
    }

    @DeleteMapping("/user")
    public ApiResponse<String> deleteUser(@RequestParam String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
        return new ApiResponse<>("User deleted successfully", null);
    }
}