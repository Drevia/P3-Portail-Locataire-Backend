package com.openclassrooms.rental.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTService {

    @Value("${JWT_TOKEN}")
    private String secret;

    public String generateToken(String username) {
        return Jwts.builder()
        .subject(username)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 1000 *60 * 60))
        .signWith(SignatureAlgorithm.HS256, secret).compact();
    }
}
