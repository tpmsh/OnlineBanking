package com.tpmsh.controllers;


import com.tpmsh.models.Account;
import com.tpmsh.models.PaymentHistory;
import com.tpmsh.models.TransactionHistory;
import com.tpmsh.models.User;
import com.tpmsh.repository.AccountRepository;
import com.tpmsh.repository.PaymentHistoryRepository;
import com.tpmsh.repository.TransactHistoryRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    @Autowired
    private TransactHistoryRepository transactHistoryRepository;

    User user;
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard(HttpSession session){
        //Get the details of the logged in user:
        user = (User) session.getAttribute("user");

        int userId =Integer.parseInt(user.getUserId());
        //Get The Accounts of The Logged In User:
        List<Account> getUserAccounts = accountRepository.getUserAccountsById(userId);

        //Get Balance:
        BigDecimal totalAccountsBalance = accountRepository.getTotalBalance(userId);

        //Set Objects:
        Map<String, Object> response = new HashMap<>();
        response.put("userAccounts", getUserAccounts);
        response.put("totalBalance", totalAccountsBalance);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/payment_history")
    public ResponseEntity<?> getPaymentHistory(HttpSession session){
        //Get the details of the logged in user:
        user = (User) session.getAttribute("user");


        int userId =Integer.parseInt(user.getUserId());

        //Get Payment History Records
        List<PaymentHistory> userPaymentHistory = paymentHistoryRepository.getPaymentsRecordsById(userId);

        Map<String, List> response = new HashMap<>();
        response.put("payment_history", userPaymentHistory);


        return ResponseEntity.ok(response);

    }

    @GetMapping("/transaction_history")
    public ResponseEntity<?> getTransactiontHistory(HttpSession session){
        //Get the details of the logged in user:
        user = (User) session.getAttribute("user");


        int userId =Integer.parseInt(user.getUserId());

        //Get Transaction History Records
        List<TransactionHistory> userTransactionHistory = transactHistoryRepository.getTransactionRecordsById(userId);

        Map<String, List> response = new HashMap<>();
        response.put("transaction_history", userTransactionHistory);


        return ResponseEntity.ok(response);

    }


    @PostMapping("/account_transaction_history")
    public ResponseEntity<?> getAccountTransactiontHistory(@RequestBody Map<String, String> requestMap,HttpSession session){
        //Get the details of the logged in user:
        String account_id = requestMap.get("account_id");
        int accountId = Integer.parseInt(account_id);

        //Get Transaction History Records
        List<TransactionHistory> accountTransactionHistory = transactHistoryRepository.getTransactionRecordsByAccountId(accountId);

        Map<String, List> response = new HashMap<>();
        response.put("transaction_history", accountTransactionHistory);


        return ResponseEntity.ok(response);

    }



}



