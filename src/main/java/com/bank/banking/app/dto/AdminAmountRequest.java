package com.bank.banking.app.dto;

public class AdminAmountRequest {

    private Long userId;
    private Double amount;

    public AdminAmountRequest() {}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}