package com.tpmsh.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Payment {

    @Id
    private int paymentId;
    private int accountId;
    private String beneficiary;
    private String beneficiaryAccNo;
    private double amount;
    private String referenceNo;
    private String status;
    private String reasonCode;
    private LocalDateTime createdAt;
}