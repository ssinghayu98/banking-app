package com.bank.banking.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore; // ✅ IMPORTANT
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ RELATION WITH USER (FIXED)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore   // 🔥 THIS FIXES INFINITE JSON LOOP
    private User user;

    // ✅ TYPE: DEPOSIT / WITHDRAW / TRANSFER
    private String type;

    private Double amount;

    // ✅ FOR TRANSFER (optional but future-ready)
    private String sender;
    private String receiver;

    private LocalDateTime timestamp;

    // ---------------- CONSTRUCTORS ----------------

    public Transaction() {}

    // ✅ BEST CONSTRUCTOR
    public Transaction(User user, String type, Double amount) {
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    // ✅ FULL CONSTRUCTOR (future transfer)
    public Transaction(User user, String type, Double amount, String sender, String receiver) {
        this.user = user;
        this.type = type;
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = LocalDateTime.now();
    }

    // ---------------- GETTERS ----------------

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // ---------------- SETTERS ----------------

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}