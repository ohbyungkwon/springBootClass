package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(String id);

    User findByEmail(String email);

    @Query("select u from User u where u.lastLogined > ?1")
    List<User> findUsersByLastLogined(Date date);

}
