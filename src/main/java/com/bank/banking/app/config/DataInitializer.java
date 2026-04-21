package com.bank.banking.app.config;

import com.bank.banking.app.model.User;
import com.bank.banking.app.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {

        String adminUsername = "admin2";

        // ✅ Check if user already exists
        if (userRepository.findByUsername(adminUsername) == null) {

            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setPassword("12345"); // plain text for now
            admin.setBalance(1000.0);

            userRepository.save(admin);

            System.out.println("✅ Admin user created");
        } else {
            System.out.println("✅ Admin already exists");
        }
    }
}