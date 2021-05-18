package com.example.demo.repository;

import com.example.demo.domain.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    @Query("select count(l.seq) from LoginHistory l where l.username=?1 and l.isSuccess=?2")
    int countLoginHistoryByUsername(String username, boolean isSuccess);
}
