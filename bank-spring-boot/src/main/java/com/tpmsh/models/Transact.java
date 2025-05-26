package com.tpmsh.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transact {
    @Id
    private int transactionId;
    private int accountId;
    private String transactionType;
    private double amount;
    private String source;
    private String status;
    private String reasonCode;
}