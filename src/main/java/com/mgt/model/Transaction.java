package com.mgt.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titel;
    private String description;
    private String transactionId;
    private String upiLink;
    private Double amount;
    private String status; // PENDING, SUCCESS, FAILED

    private LocalDateTime createdAt;

    private String imString;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    // Default constructor
    public Transaction() {
        this.createdAt = LocalDateTime.now();
    }

    // All-args constructor
    public Transaction(Long id, String transactionId, String upiLink, Double amount, String status,
                       LocalDateTime createdAt, User user) {
        this.id = id;
        this.transactionId = transactionId;
        this.upiLink = upiLink;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.user = user;
    }

    

    // Getters and setters

    public Transaction(Long id, String transactionId, String upiLink, Double amount, String status,
            LocalDateTime createdAt, String imString, User user) {
        this.id = id;
        this.transactionId = transactionId;
        this.upiLink = upiLink;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.imString = imString;
        this.user = user;
    }

    

    public Transaction(Long id, String titel, String description, String transactionId, String upiLink, Double amount,
            String status, LocalDateTime createdAt, String imString, User user) {
        this.id = id;
        this.titel = titel;
        this.description = description;
        this.transactionId = transactionId;
        this.upiLink = upiLink;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
        this.imString = imString;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUpiLink() {
        return upiLink;
    }

    public void setUpiLink(String upiLink) {
        this.upiLink = upiLink;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImString() {
        return imString;
    }

    public void setImString(String imString) {
        this.imString = imString;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    

    
}
