package com.bank.banking.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    // ===============================
    // 🔑 PRIMARY KEY
    // ===============================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===============================
    // 👤 USERNAME
    // ===============================
    @Column(unique = true, nullable = false)
    private String username;

    // ===============================
    // 🔐 PASSWORD (HIDDEN IN RESPONSE)
    // ===============================
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    // ===============================
    // 💰 BALANCE
    // ===============================
    @Column(nullable = false)
    private Double balance = 0.0;

    // ===============================
    // 🛡 ROLE (USER / ADMIN)
    // ===============================
    @Column(nullable = false)
    private String role = "USER";

    // ===============================
    // 🔁 RELATION (FIXED RECURSION)
    // ===============================
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    // ===============================
    // 🏗 CONSTRUCTORS
    // ===============================
    public User() {}

    public User(String username, String password, Double balance, String role) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.role = role;
    }

    // ===============================
    // 📥 GETTERS
    // ===============================
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

    // ===============================
    // 📤 SETTERS
    // ===============================
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