package com.bank.banking.app.repository;

import com.bank.banking.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 🔥 REQUIRED for login
    User findByUsername(String username);
}