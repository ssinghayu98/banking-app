package com.bank.banking.app.dto;

public class UserResponseDto {

    private Long id;
    private String username;
    private Double balance;
    private String role;

    // ✅ GETTERS
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Double getBalance() {
        return balance;
    }

    public String getRole() {
        return role;
    }

    // ✅ SETTERS
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setRole(String role) {
        this.role = role;
    }
}