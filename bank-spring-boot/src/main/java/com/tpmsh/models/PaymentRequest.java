package com.tpmsh.BankApp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private String beneficiary;
    private String accountNumber;
    private String accountId;
    private String reference;
    private String paymentAmount;
}