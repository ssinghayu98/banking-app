package com.bank.banking.app.controller;

import com.bank.banking.app.dto.ApiResponse;
import com.bank.banking.app.dto.AmountRequest;
import com.bank.banking.app.dto.TransferRequest;
import com.bank.banking.app.model.Transaction;
import com.bank.banking.app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 💰 BALANCE
    @GetMapping("/balance")
    public ApiResponse<Double> getBalance(@RequestParam String username) {
        return new ApiResponse<>("Balance fetched successfully",
                userService.getBalance(username));
    }

    // ➕ DEPOSIT
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody AmountRequest req) {
        try {
            validate(req.getUsername(), req.getAmount());

            userService.deposit(req.getUsername(), req.getAmount());

            return ResponseEntity.ok(new ApiResponse<>("Deposit successful", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    // ➖ WITHDRAW
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody AmountRequest req) {
        try {
            validate(req.getUsername(), req.getAmount());

            userService.withdraw(req.getUsername(), req.getAmount());

            return ResponseEntity.ok(new ApiResponse<>("Withdraw successful", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    // 💸 TRANSFER
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest req) {
        try {
            if (req.getSender() == null || req.getReceiver() == null || req.getAmount() == null) {
                throw new RuntimeException("Missing transfer data");
            }

            userService.transfer(req.getSender(), req.getReceiver(), req.getAmount());

            return ResponseEntity.ok(new ApiResponse<>("Transfer successful", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    // 📜 TRANSACTIONS
    @GetMapping("/transactions")
    public ApiResponse<List<Transaction>> getTransactions(@RequestParam String username) {
        return new ApiResponse<>(
                "Transactions fetched successfully",
                userService.getTransactions(username)
        );
    }

    // 🔒 COMMON VALIDATION
    private void validate(String username, Double amount) {
        if (username == null || username.isEmpty()) {
            throw new RuntimeException("Username missing");
        }
        if (amount == null || amount <= 0) {
            throw new RuntimeException("Invalid amount");
        }
    }
}