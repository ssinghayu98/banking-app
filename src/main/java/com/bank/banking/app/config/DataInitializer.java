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

        // ✅ FIXED: no isEmpty()
        if (userRepository.findByUsername(adminUsername) == null) {

            User admin = new User();
            admin.setUsername(adminUsername);

            // ⚠️ plain text (matches your current login logic)
            admin.setPassword("12345");

            admin.setBalance(1000.0);
            admin.setRole("ADMIN");

            userRepository.save(admin);

            System.out.println("✅ Admin created");
        } else {
            System.out.println("✅ Admin already exists");
        }
    }
}