package com.example.demo.domain.jwt.service;


import com.example.demo.domain.jwt.repository.RefreshRepository;
import com.example.demo.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

@Service
public class RefreshService {
    private final RefreshRepository refreshRepository;
    private final JWTUtil jwtUtil;

    public RefreshService(RefreshRepository refreshRepository, JWTUtil jwtUtil) {
        this.refreshRepository = refreshRepository;
        this.jwtUtil = jwtUtil;
    }

    public String refresh(String refreshToken){

        Claims claims;

        try{
            claims = jwtUtil.getClaims(refreshToken);
        }catch (Exception e) {
            throw new IllegalArgumentException("유효하지 않은 Refresh Token입니다.");
        }

        String tokenType = claims.get("tokenType", String.class);

        if (!"REFRESH".equals(tokenType)) {
            throw new IllegalArgumentException("Refresh Token이 아닙니다.");
        }

        boolean exists = refreshRepository.existsByRefresh(refreshToken);

        if (!exists) {
            throw new IllegalArgumentException("로그아웃되었거나 만료된 Refresh Token입니다.");
        }

        String username = claims.getSubject();
        String role = claims.get("role", String.class);

        return jwtUtil.createAccessToken(username, role);




    }
}
