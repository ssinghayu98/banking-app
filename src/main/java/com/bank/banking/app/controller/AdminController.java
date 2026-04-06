package com.bank.banking.app.controller;

import com.bank.banking.app.dto.TransferRequest;
import com.bank.banking.app.model.Transaction;
import com.bank.banking.app.model.User;
import com.bank.banking.app.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam Long userId, @RequestParam Double amount) {
        return userService.deposit(userId, amount);
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam Long userId, @RequestParam Double amount) {
        return userService.withdraw(userId, amount);
    }

    @GetMapping("/balance/{userId}")
    public Double checkBalance(@PathVariable Long userId) {
        return userService.checkBalance(userId);
    }

    @PostMapping("/transfer")
    public String transferMoney(@RequestBody TransferRequest request) {
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