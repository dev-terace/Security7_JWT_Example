package com.example.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private final SecretKey key;
    private final long accesstokenExpireTime;
    private final long refreshTokenExpireTime;

    public JWTUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expire-time}")  long accesstokenExpireTime,
            @Value("${jwt.refresh-token-expire-time}") long refreshTokenExpireTime) {

        this.key = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8));
        this.accesstokenExpireTime = accesstokenExpireTime;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
    }

    public String createAccessToken(String username, String role)
    {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + accesstokenExpireTime);

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("tokenType", "ACCESS")
                .issuedAt(now)
                .expiration(expireDate)
                .signWith(key)
                .compact();
    }


    public String createRefreshToken(String username, String role) {

        Date now = new Date();
        Date expireDate = new Date(now.getTime() + refreshTokenExpireTime);

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("tokenType", "REFRESH")
                .issuedAt(now)
                .expiration(expireDate)
                .signWith(key)
                .compact();
    }



    public Claims getClaims(String token)
    {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
