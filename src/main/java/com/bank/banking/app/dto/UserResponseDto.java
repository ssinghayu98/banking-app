package com.bank.banking.app.dto;

import com.bank.banking.app.model.Role;

public class UserResponseDto {

    private Long id;
    private String username;
    private Double balance;
    private Role role;

    public UserResponseDto() {
    }

    public UserResponseDto(Long id, String username, Double balance, Role role) {
        this.id = id;
        this.username = username;
        this.balance = balance;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Double getBalance() {
        return balance;
    }

    public Role getRole() {
        return role;
    }
}