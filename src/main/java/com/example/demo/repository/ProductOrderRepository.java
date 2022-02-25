package com.example.demo.repository;

import com.example.demo.domain.ProductOrder;
import com.example.demo.domain.User;
import com.example.demo.repository.search.SearchProductOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long>, SearchProductOrderRepository {
    Boolean existsByProductIdAndUserId(Long productId, Long userId);
}