
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
@RequestMapping("/api/user") // ✅ FIXED (important for your frontend URL)
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
    // ➕ DEPOSIT (DEBUG + SAFE)
    // ===============================
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody AmountRequest req) {
        try {
            System.out.println("Deposit Request: username=" + req.getUsername() + ", amount=" + req.getAmount());

            if (req.getUsername() == null || req.getAmount() == null) {
                throw new RuntimeException("Username or Amount is missing");
            }

            if (req.getAmount() <= 0) {
                throw new RuntimeException("Invalid amount");
            }

            userService.deposit(req.getUsername(), req.getAmount());

            return ResponseEntity.ok(new ApiResponse<>("Deposit successful", null));

        } catch (Exception e) {
            e.printStackTrace(); // 🔥 THIS will show real error in Railway
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    // ===============================
    // ➖ WITHDRAW
    // ===============================
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody AmountRequest req) {
        try {
            userService.withdraw(req.getUsername(), req.getAmount());
            return ResponseEntity.ok(new ApiResponse<>("Withdraw successful", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    // ===============================
    // 💸 TRANSFER
    // ===============================
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest req) {
        try {
            userService.transfer(
                    req.getSender(),
                    req.getReceiver(),
                    req.getAmount()
            );
            return ResponseEntity.ok(new ApiResponse<>("Transfer successful", null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
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