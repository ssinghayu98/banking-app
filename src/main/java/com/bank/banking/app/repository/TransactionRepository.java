package com.bank.banking.app.repository;

import com.bank.banking.app.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // ✅ IMPORTANT (matches UserService)
    List<Transaction> findByUserUsername(String username);
}