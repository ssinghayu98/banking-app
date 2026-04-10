package com.bank.banking.app.controller;

import com.bank.banking.app.dto.AdminAmountRequest;
import com.bank.banking.app.dto.AdminTransferRequest;
import com.bank.banking.app.dto.TransactionResponseDto;
import com.bank.banking.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PostMapping("/deposit")
    public String deposit(@RequestBody AdminAmountRequest request) {
        return userService.deposit(request.getUserId(), request.getAmount());
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestBody AdminAmountRequest request) {
        return userService.withdraw(request.getUserId(), request.getAmount());
    }

    @PostMapping("/transfer")
    public String transferMoney(@RequestBody AdminTransferRequest request) {
        return userService.transferMoney(
                request.getSenderId(),
                request.getReceiverId(),
                request.getAmount()
        );
    }

    @GetMapping("/transactions/{userId}")
    public List<TransactionResponseDto> getTransactions(@PathVariable Long userId) {
        return userService.getTransactionsByUserId(userId);
    }
}