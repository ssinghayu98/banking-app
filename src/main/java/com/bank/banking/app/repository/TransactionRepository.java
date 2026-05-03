package com.bank.banking.app.repository;

import com.bank.banking.app.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // ❌ old (keep if you want, but not reliable in prod)
    // List<Transaction> findByUser(User user);

    // ✅ NEW (reliable)
    List<Transaction> findByUserUsername(String username);
}