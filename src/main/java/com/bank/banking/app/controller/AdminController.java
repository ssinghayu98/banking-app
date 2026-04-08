package com.bank.banking.app.controller;

import com.bank.banking.app.dto.ApiResponse;
import com.bank.banking.app.dto.TransferRequest;
import com.bank.banking.app.model.Transaction;
import com.bank.banking.app.model.User;
import com.bank.banking.app.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return new ApiResponse<>("User registered successfully", savedUser);
    }

    @GetMapping("/users")
    public ApiResponse<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ApiResponse<>("Users fetched successfully", users);
    }

    @DeleteMapping("/user/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        String message = userService.deleteUser(id);
        return new ApiResponse<>(message, null);
    }

    @PostMapping("/deposit")
    public ApiResponse<Void> deposit(@RequestParam Long userId, @RequestParam Double amount) {
        String message = userService.deposit(userId, amount);
        return new ApiResponse<>(message, null);
    }

    @PostMapping("/withdraw")
    public ApiResponse<Void> withdraw(@RequestParam Long userId, @RequestParam Double amount) {
        String message = userService.withdraw(userId, amount);
        return new ApiResponse<>(message, null);
    }

    @GetMapping("/balance/{userId}")
    public ApiResponse<Double> checkBalance(@PathVariable Long userId) {
        Double balance = userService.checkBalance(userId);
        return new ApiResponse<>("Balance fetched successfully", balance);
    }

    @PostMapping("/transfer")
    public ApiResponse<Void> transferMoney(@RequestBody TransferRequest request) {
        String message = userService.transferMoney(
                request.getSenderId(),
                request.getReceiverId(),
                request.getAmount()
        );
        return new ApiResponse<>(message, null);
    }

    @GetMapping("/transactions")
    public ApiResponse<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = userService.getAllTransactions();
        return new ApiResponse<>("Transactions fetched successfully", transactions);
    }
}