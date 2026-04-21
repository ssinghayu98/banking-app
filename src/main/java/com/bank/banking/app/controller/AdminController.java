package com.bank.banking.app.controller;

import com.bank.banking.app.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    // Constructor Injection (FIXES your error)
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam Long userId, @RequestParam Double amount) {
        return userService.deposit(userId, amount);
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam Long userId, @RequestParam Double amount) {
        return userService.withdraw(userId, amount);
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam Long senderId,
                           @RequestParam Long receiverId,
                           @RequestParam Double amount) {
        return userService.transferMoney(senderId, receiverId, amount);
    }

    @GetMapping("/transactions")
    public Object getTransactions(@RequestParam Long userId) {
        return userService.getTransactionsByUserId(userId);
    }
}