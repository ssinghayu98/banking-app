package com.bank.banking.app.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private Double balance = 0.0;

    // ✅ ROLE (for future admin/user separation)
    private String role;

    // ✅ RELATION WITH TRANSACTIONS
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    // ---------------- CONSTRUCTORS ----------------
    public User() {}

    public User(String username, String password, Double balance, String role) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.role = role;
    }

    // ---------------- GETTERS ----------------
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Double getBalance() {
        return balance;
    }

    public String getRole() {
        return role;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    // ---------------- SETTERS ----------------
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}