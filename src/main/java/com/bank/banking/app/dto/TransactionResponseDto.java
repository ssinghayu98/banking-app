package com.bank.banking.app.dto;

public class TransactionResponseDto {

    private Long id;
    private Long senderId;
    private Long receiverId;
    private Double amount;
    private String type;

    public TransactionResponseDto() {
    }

    public TransactionResponseDto(Long id, Long senderId, Long receiverId, Double amount, String type) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public Double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }
}