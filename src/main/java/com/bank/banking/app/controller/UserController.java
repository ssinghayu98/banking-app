package com.bank.banking.app.controller;

import com.bank.banking.app.dto.ApiResponse;
import com.bank.banking.app.model.Transaction;
import com.bank.banking.app.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ===============================
    // 💰 GET BALANCE
    // ===============================
    @GetMapping("/balance")
    public ApiResponse<Double> getBalance(@RequestParam String username) {

        double balance = userService.getBalance(username);

        return new ApiResponse<>("Balance fetched successfully", balance);
    }

    // ===============================
    // ➕ DEPOSIT
    // ===============================
    @PostMapping("/deposit")
    public ApiResponse<String> deposit(@RequestParam String username,
                                       @RequestParam Double amount) {

        userService.deposit(username, amount);

        return new ApiResponse<>("Deposit successful", null);
    }

    // ===============================
    // ➖ WITHDRAW
    // ===============================
    @PostMapping("/withdraw")
    public ApiResponse<String> withdraw(@RequestParam String username,
                                        @RequestParam Double amount) {

        userService.withdraw(username, amount);

        return new ApiResponse<>("Withdraw successful", null);
    }

    // ===============================
    // 💸 TRANSFER (NEW)
    // ===============================
    @PostMapping("/transfer")
    public ApiResponse<String> transfer(
            @RequestParam String sender,
            @RequestParam String receiver,
            @RequestParam Double amount) {

        userService.transfer(sender, receiver, amount);

        return new ApiResponse<>("Transfer successful", null);
    }

    // ===============================
    // 📜 GET TRANSACTIONS
    // ===============================
    @GetMapping("/transactions")
    public ApiResponse<List<Transaction>> getTransactions(@RequestParam String username) {

        List<Transaction> transactions = userService.getTransactions(username);

        return new ApiResponse<>("Transactions fetched successfully", transactions);
    }
}