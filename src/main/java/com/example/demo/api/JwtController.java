package com.example.demo.api;

import com.example.demo.domain.jwt.dto.RefreshRequestDTO;
import com.example.demo.domain.jwt.service.RefreshService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class JwtController {
    private final RefreshService refreshService;


    public JwtController(RefreshService refreshService) {
        this.refreshService = refreshService;
    }


    @PostMapping("/jwt/refresh")
    public String refresh(@RequestBody RefreshRequestDTO dto){

        return refreshService.refresh(dto.refreshToken());
    }
}
