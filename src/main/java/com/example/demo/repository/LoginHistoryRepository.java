package com.example.demo.repository;

import com.example.demo.domain.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    @Query("select count(l.seq) from LoginHistory l where l.username=?1 and l.success='false'")
    int countLoginHistoryByUsername(String username);
}
