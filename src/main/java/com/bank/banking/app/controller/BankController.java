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
    public ApiResponse<String> deposit(@Valid @RequestBody AmountRequest request, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        String result = userService.deposit(user.getId(), request.getAmount());

        return new ApiResponse<>("Deposit successful", result);
    }

    @PostMapping("/withdraw")
    public ApiResponse<String> withdraw(@Valid @RequestBody AmountRequest request, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        String result = userService.withdraw(user.getId(), request.getAmount());

        return new ApiResponse<>("Withdrawal successful", result);
    }

    @GetMapping("/balance")
    public ApiResponse<Double> checkBalance(Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        Double balance = userService.checkBalance(user.getId());

        return new ApiResponse<>("Balance fetched successfully", balance);
    }

    @PostMapping("/transfer")
    public ApiResponse<String> transfer(@Valid @RequestBody TransferRequest request, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        String result = userService.transferMoney(
                user.getId(),
                request.getReceiverId(),
                request.getAmount()
        );

        return new ApiResponse<>("Transfer successful", result);
    }

    @GetMapping("/transactions")
    public ApiResponse<List<TransactionResponseDto>> getAllTransactions(Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        List<TransactionResponseDto> transactions =
                userService.getTransactionsByUserId(user.getId());

        return new ApiResponse<>("Transactions fetched successfully", transactions);
    }
}