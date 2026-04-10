package com.bank.banking.app.controller;

import com.bank.banking.app.dto.AmountRequest;
import com.bank.banking.app.dto.ApiResponse;
import com.bank.banking.app.dto.TransactionResponseDto;
import com.bank.banking.app.dto.TransferRequest;
import com.bank.banking.app.model.User;
import com.bank.banking.app.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
@RequiredArgsConstructor
public class BankController {

    private final UserService userService;

    @PostMapping("/deposit")
    public String deposit(@Valid @RequestBody AmountRequest request, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        return userService.deposit(user.getId(), request.getAmount());
    }

    @PostMapping("/withdraw")
    public String withdraw(@Valid @RequestBody AmountRequest request, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        return userService.withdraw(user.getId(), request.getAmount());
    }

    @GetMapping("/balance")
    public ApiResponse<Double> checkBalance(Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        Double balance = userService.checkBalance(user.getId());

        return new ApiResponse<>(
                "Balance fetched successfully",
                balance
        );
    }

    @PostMapping("/transfer")
    public String transfer(@Valid @RequestBody TransferRequest request, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());

        return userService.transferMoney(
                user.getId(),
                request.getReceiverId(),
                request.getAmount()
        );
    }

    @GetMapping("/transactions")
    public List<TransactionResponseDto> getAllTransactions(Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        return userService.getTransactionsByUserId(user.getId());
    }
}