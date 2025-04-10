package com.tpmsh.BankApp.helpers.authorization;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.security.Key;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);
    @Value("${bankApp.secret}")
    private String appSecret;

    @Value("${bankApp.expires}")
    private long expiresIn;


    public String generateToken(String userEmail) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiresIn);

        Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Jwts.builder()
                .setSubject(userEmail)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256,appSecret)
                .compact();
    }

    public Claims decodeToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(appSecret)
                    .parseClaimsJws(token);
            return claimsJws.getBody();
        } catch (Exception e) {
            log.info("Token is not valid.");
            return null;
        }
    }

    public boolean isTokenIncluded(String req){
        return req != null;
    }

    public String getAccessTokenFromHeader(String req){
        String[] parts = req.split(" ");
        return parts[1];
    }

}