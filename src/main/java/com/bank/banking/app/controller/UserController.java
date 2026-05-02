package com.bank.banking.app.controller;

import com.bank.banking.app.dto.ApiResponse;
import com.bank.banking.app.dto.AmountRequest;
import com.bank.banking.app.dto.TransferRequest;
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
    // ➕ DEPOSIT (FIXED)
    // ===============================
    @PostMapping("/deposit")
    public ApiResponse<String> deposit(@RequestBody AmountRequest req) {

        userService.deposit(req.getUsername(), req.getAmount());

        return new ApiResponse<>("Deposit successful", null);
    }

    // ===============================
    // ➖ WITHDRAW (FIXED)
    // ===============================
    @PostMapping("/withdraw")
    public ApiResponse<String> withdraw(@RequestBody AmountRequest req) {

        userService.withdraw(req.getUsername(), req.getAmount());

        return new ApiResponse<>("Withdraw successful", null);
    }

    // ===============================
    // 💸 TRANSFER (FIXED)
    // ===============================
    @PostMapping("/transfer")
    public ApiResponse<String> transfer(@RequestBody TransferRequest req) {

        userService.transfer(
                req.getSender(),
                req.getReceiver(),
                req.getAmount()
        );

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