package com.tpmsh.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "v_transaction_history")
public class TransactionHistory {

    @Id
    private int transactionId;
    private int accountId;
    private String transactionType;
    private double amount;
    private String source;
    private String status;
    private String reasonCode;
    private LocalDateTime createdAt;
}