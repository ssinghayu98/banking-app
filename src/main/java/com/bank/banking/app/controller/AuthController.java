package com.bank.banking.app.controller;

import com.bank.banking.app.model.User;
import com.bank.banking.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        User dbUser = userRepository.findByUsername(user.getUsername());

        if (dbUser != null && dbUser.getPassword().equals(user.getPassword())) {
            return "SUCCESS";
        }

        return "FAIL";
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        userRepository.save(user);
        return "REGISTERED";
    }
}