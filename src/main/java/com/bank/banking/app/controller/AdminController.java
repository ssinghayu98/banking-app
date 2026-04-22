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

    // ===============================
    // ➕ ADMIN DEPOSIT
    // ===============================
    @PostMapping("/deposit")
    public String deposit(@RequestParam String username,
                          @RequestParam Double amount) {

        userService.deposit(username, amount); // ✅ call only

        return "Admin deposit successful";     // ✅ return message
    }

    // ===============================
    // ➖ ADMIN WITHDRAW
    // ===============================
    @PostMapping("/withdraw")
    public String withdraw(@RequestParam String username,
                           @RequestParam Double amount) {

        userService.withdraw(username, amount);

        return "Admin withdraw successful";
    }
}