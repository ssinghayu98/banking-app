package com.bank.banking.app.dto;

public class AmountRequest {

    private String username;
    private Double amount;

    public AmountRequest() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}