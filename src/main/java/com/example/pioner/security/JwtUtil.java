package com.example.pioner.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import javax.crypto.SecretKey;

public class JwtUtil
{

    private static final String SECRET_KEY = "mySecretKey1234567890123456789012";
    private static final long EXPIRATION_TIME = 86400000; // 24 hour

    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public static String generateToken(final Long userId)
    {
        return Jwts.builder()
            .subject(String.valueOf(userId))
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(key)
            .compact();
    }

    public static Claims extractClaims(final String token)
    {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public static boolean validateToken(final String token)
    {
        return !isTokenExpired(token);
    }

    private static boolean isTokenExpired(String token)
    {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
