package com.bank.banking.app.controller;

import com.bank.banking.app.dto.AmountRequest;
import com.bank.banking.app.dto.TransferRequest;
import com.bank.banking.app.model.Transaction;
import com.bank.banking.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
@RequiredArgsConstructor
public class BankController {

    private final UserService userService;

    @PostMapping("/deposit")
    public String deposit(@RequestBody AmountRequest request) {
        return userService.deposit(request.getUserId(), request.getAmount());
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestBody AmountRequest request) {
        return userService.withdraw(request.getUserId(), request.getAmount());
    }

    @GetMapping("/balance/{userId}")
    public Double checkBalance(@PathVariable Long userId) {
        return userService.checkBalance(userId);
    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferRequest request) {
        return userService.transferMoney(
                request.getSenderId(),
                request.getReceiverId(),
                request.getAmount()
        );
    }

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return userService.getAllTransactions();
    }
}