package com.bank.banking.app.controller;

import com.bank.banking.app.model.User;
import com.bank.banking.app.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class BankController {

    private final UserService userService;

    public BankController(UserService userService) {
        this.userService = userService;
    }

    // ✅ Get balance
    @GetMapping("/balance/{username}")
    public Map<String, Double> getBalance(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return Map.of("balance", user.getBalance());
    }

    // 💰 Deposit
    @PostMapping("/deposit")
    public String deposit(@RequestParam String username,
                          @RequestParam Double amount) {

        return userService.depositByUsername(username, amount);
    }

    // 💸 Withdraw
    @PostMapping("/withdraw")
    public String withdraw(@RequestParam String username,
                           @RequestParam Double amount) {

        return userService.withdrawByUsername(username, amount);
    }
}