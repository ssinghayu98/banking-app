package com.bank.banking.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private Double balance = 0.0;
    private String role = "USER";

    // GETTERS & SETTERS

    public Long getId() { return id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public Double getBalance() { return balance; }

    public void setBalance(Double balance) { this.balance = balance; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }
}