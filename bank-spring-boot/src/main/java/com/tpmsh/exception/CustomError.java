package com.tpmsh.BankApp.exception;

public class CustomError extends RuntimeException {

    private int statusCode;

    public CustomError(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
