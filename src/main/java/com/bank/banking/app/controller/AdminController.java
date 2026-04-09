package com.bank.banking.app.controller;

import com.bank.banking.app.dto.ApiResponse;
import com.bank.banking.app.dto.TransferRequest;
import com.bank.banking.app.model.Transaction;
import com.bank.banking.app.model.User;
import com.bank.banking.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody User user) {
        logger.info("Admin request to register user with username: {}", user.getUsername());

        User savedUser = userService.registerUser(user);

        logger.info("User registered successfully with username: {}", savedUser.getUsername());

        return new ApiResponse<>("User registered successfully", savedUser);
    }

    @GetMapping("/users")
    public ApiResponse<List<User>> getAllUsers() {
        logger.info("Admin request to fetch all users");

        List<User> users = userService.getAllUsers();

        logger.info("Fetched all users successfully. Count: {}", users.size());

        return new ApiResponse<>("Users fetched successfully", users);
    }

    @DeleteMapping("/user/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        logger.info("Admin request to delete user with ID: {}", id);

        String message = userService.deleteUser(id);

        logger.info("User deleted successfully with ID: {}", id);

        return new ApiResponse<>(message, null);
    }

    @PostMapping("/deposit")
    public ApiResponse<Void> deposit(@RequestParam Long userId, @RequestParam Double amount) {
        logger.info("Admin request to deposit amount {} to user ID {}", amount, userId);

        String message = userService.deposit(userId, amount);

        logger.info("Deposit successful. Amount {} deposited to user ID {}", amount, userId);

        return new ApiResponse<>(message, null);
    }

    @PostMapping("/withdraw")
    public ApiResponse<Void> withdraw(@RequestParam Long userId, @RequestParam Double amount) {
        logger.info("Admin request to withdraw amount {} from user ID {}", amount, userId);

        String message = userService.withdraw(userId, amount);

        logger.info("Withdraw successful. Amount {} withdrawn from user ID {}", amount, userId);

        return new ApiResponse<>(message, null);
    }

    @GetMapping("/balance/{userId}")
    public ApiResponse<Double> checkBalance(@PathVariable Long userId) {
        logger.info("Admin request to check balance for user ID: {}", userId);

        Double balance = userService.checkBalance(userId);

        logger.info("Balance fetched successfully for user ID: {}", userId);

        return new ApiResponse<>("Balance fetched successfully", balance);
    }

    @PostMapping("/transfer")
    public ApiResponse<Void> transferMoney(@RequestBody TransferRequest request) {
        logger.info("Admin request to transfer amount {} from user ID {} to user ID {}",
                request.getAmount(), request.getSenderId(), request.getReceiverId());

        String message = userService.transferMoney(
                request.getSenderId(),
                request.getReceiverId(),
                request.getAmount()
        );

        logger.info("Transfer successful. Amount {} transferred from user ID {} to user ID {}",
                request.getAmount(), request.getSenderId(), request.getReceiverId());

        return new ApiResponse<>(message, null);
    }

    @GetMapping("/transactions")
    public ApiResponse<List<Transaction>> getAllTransactions() {
        logger.info("Admin request to fetch all transactions");

        List<Transaction> transactions = userService.getAllTransactions();

        logger.info("Fetched all transactions successfully. Count: {}", transactions.size());

        return new ApiResponse<>("Transactions fetched successfully", transactions);
    }
}