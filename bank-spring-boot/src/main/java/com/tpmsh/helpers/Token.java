package com.tpmsh.helpers;

import static java.util.UUID.randomUUID;

public class Token {
    public static String generateToken(){
        return randomUUID().toString();
    }
}
