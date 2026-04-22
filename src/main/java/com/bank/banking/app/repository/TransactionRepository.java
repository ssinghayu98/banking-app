package com.bank.banking.app.repository;

import com.bank.banking.app.model.Transaction;
import com.bank.banking.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUser(User user);
}