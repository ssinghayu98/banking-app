package com.bank.banking.app.controller;

import com.bank.banking.app.dto.ApiResponse;
import com.bank.banking.app.dto.TransactionResponseDto;
import com.bank.banking.app.dto.TransferRequest;
import com.bank.banking.app.dto.UserResponseDto;
import com.bank.banking.app.model.User;
import com.bank.banking.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
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
    public ApiResponse<UserResponseDto> register(@RequestBody User user, Authentication authentication) {
        String adminUsername = authentication.getName();

        logger.info("AUDIT -> Admin '{}' requested registration for username '{}'", adminUsername, user.getUsername());

        UserResponseDto savedUser = userService.registerUser(user);

        logger.info("AUDIT -> Admin '{}' successfully registered user '{}'", adminUsername, savedUser.getUsername());

        return new ApiResponse<>("User registered successfully", savedUser);
    }

    @GetMapping("/users")
    public ApiResponse<List<UserResponseDto>> getAllUsers(Authentication authentication) {
        String adminUsername = authentication.getName();

        logger.info("AUDIT -> Admin '{}' requested all users", adminUsername);

        List<UserResponseDto> users = userService.getAllUsers();

        logger.info("AUDIT -> Admin '{}' fetched all users successfully. Count: {}", adminUsername, users.size());

        return new ApiResponse<>("Users fetched successfully", users);
    }

    @DeleteMapping("/user/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id, Authentication authentication) {
        String adminUsername = authentication.getName();

        logger.info("AUDIT -> Admin '{}' requested deletion of user ID {}", adminUsername, id);

        String message = userService.deleteUser(id);

        logger.info("AUDIT -> Admin '{}' successfully deleted user ID {}", adminUsername, id);

        return new ApiResponse<>(message, null);
    }

    @PostMapping("/deposit")
    public ApiResponse<Void> deposit(@RequestParam Long userId, @RequestParam Double amount, Authentication authentication) {
        String adminUsername = authentication.getName();

        logger.info("AUDIT -> Admin '{}' requested deposit of amount {} to user ID {}", adminUsername, amount, userId);

        String message = userService.deposit(userId, amount);

        logger.info("AUDIT -> Admin '{}' successfully deposited amount {} to user ID {}", adminUsername, amount, userId);

        return new ApiResponse<>(message, null);
    }

    @PostMapping("/withdraw")
    public ApiResponse<Void> withdraw(@RequestParam Long userId, @RequestParam Double amount, Authentication authentication) {
        String adminUsername = authentication.getName();

        logger.info("AUDIT -> Admin '{}' requested withdrawal of amount {} from user ID {}", adminUsername, amount, userId);

        String message = userService.withdraw(userId, amount);

        logger.info("AUDIT -> Admin '{}' successfully withdrew amount {} from user ID {}", adminUsername, amount, userId);

        return new ApiResponse<>(message, null);
    }

    @GetMapping("/balance/{userId}")
    public ApiResponse<Double> checkBalance(@PathVariable Long userId, Authentication authentication) {
        String adminUsername = authentication.getName();

        logger.info("AUDIT -> Admin '{}' requested balance for user ID {}", adminUsername, userId);

        Double balance = userService.checkBalance(userId);

        logger.info("AUDIT -> Admin '{}' successfully fetched balance for user ID {}", adminUsername, userId);

        return new ApiResponse<>("Balance fetched successfully", balance);
    }

    @PostMapping("/transfer")
    public ApiResponse<Void> transferMoney(@RequestBody TransferRequest request, Authentication authentication) {
        String adminUsername = authentication.getName();

        logger.info("AUDIT -> Admin '{}' requested transfer of amount {} from user ID {} to user ID {}",
                adminUsername, request.getAmount(), request.getSenderId(), request.getReceiverId());

        String message = userService.transferMoney(
                request.getSenderId(),
                request.getReceiverId(),
                request.getAmount()
        );

        logger.info("AUDIT -> Admin '{}' successfully transferred amount {} from user ID {} to user ID {}",
                adminUsername, request.getAmount(), request.getSenderId(), request.getReceiverId());

        return new ApiResponse<>(message, null);
    }

    @GetMapping("/transactions")
    public ApiResponse<List<TransactionResponseDto>> getAllTransactions(Authentication authentication) {
        String adminUsername = authentication.getName();

        logger.info("AUDIT -> Admin '{}' requested all transactions", adminUsername);

        List<TransactionResponseDto> transactions = userService.getAllTransactions();

        logger.info("AUDIT -> Admin '{}' fetched all transactions successfully. Count: {}", adminUsername, transactions.size());

        return new ApiResponse<>("Transactions fetched successfully", transactions);
    }
}