package com.bank.banking.app.config;

import com.bank.banking.app.model.Role;
import com.bank.banking.app.model.User;
import com.bank.banking.app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        String adminUsername = "admin2";

        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode("12345"));
            admin.setBalance(0.0);
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);

            logger.info("DEFAULT ADMIN CREATED SUCCESSFULLY -> username: {}", adminUsername);
        } else {
            logger.info("DEFAULT ADMIN ALREADY EXISTS -> username: {}", adminUsername);
        }
    }
}