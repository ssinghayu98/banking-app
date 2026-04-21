package com.bank.banking.app.controller;

import com.bank.banking.app.dto.ApiResponse;
import com.bank.banking.app.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class BankController {

    private final UserService userService;

    public BankController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/deposit")
    public ApiResponse<String> deposit(@RequestParam String username,
                                       @RequestParam Double amount) {

        return new ApiResponse<>(userService.deposit(username, amount), null);
    }

    @PostMapping("/withdraw")
    public ApiResponse<String> withdraw(@RequestParam String username,
                                        @RequestParam Double amount) {

        return new ApiResponse<>(userService.withdraw(username, amount), null);
    }

    @GetMapping("/balance")
    public ApiResponse<Double> getBalance(@RequestParam String username) {

        return new ApiResponse<>("Balance fetched",
                userService.getBalance(username));
    }

    @GetMapping("/transactions")
    public ApiResponse<List<?>> getTransactions(@RequestParam String username) {

        return new ApiResponse<>("Transactions fetched",
                userService.getTransactions(username));
    }
}