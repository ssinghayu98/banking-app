package com.bank.banking.app.controller;

import com.bank.banking.app.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // ✅ DEPOSIT
    @PostMapping("/deposit")
    public String deposit(@RequestParam String username,
                          @RequestParam Double amount) {

        return userService.deposit(username, amount);
    }

    // ✅ WITHDRAW
    @PostMapping("/withdraw")
    public String withdraw(@RequestParam String username,
                           @RequestParam Double amount) {

        return userService.withdraw(username, amount);
    }
}