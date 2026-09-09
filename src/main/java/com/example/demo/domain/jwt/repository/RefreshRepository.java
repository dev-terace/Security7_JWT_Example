package com.example.demo.domain.jwt.repository;

import com.example.demo.domain.jwt.entity.RefreshEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {
    boolean existsByRefresh(String refresh);

}
