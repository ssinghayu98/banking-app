package com.bank.banking.app.controller;

import com.bank.banking.app.model.User;
import com.bank.banking.app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*") // allow React frontend
public class BankController {

    private final UserService userService;

    // Constructor Injection
    public BankController(UserService userService) {
        this.userService = userService;
    }

    // ✅ Get User Profile
    @GetMapping("/profile")
    public ResponseEntity<?> getUser(@RequestParam String username) {
        User user = userService.getUserByUsername(username);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        return ResponseEntity.ok(user);
    }

    // ✅ Get Balance (FIXED)
    @GetMapping("/balance/{username}")
    public ResponseEntity<?> getBalance(@PathVariable String username) {

        User user = userService.getUserByUsername(username);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        return ResponseEntity.ok(Map.of("balance", user.getBalance()));
    }
}