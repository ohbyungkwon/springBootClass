package com.example.demo.repository;

import com.example.demo.domain.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    Optional<ProductOrder> findByProductIdAndUserId(Long productId, Long userId);

    Boolean existsByProductIdAndUserId(Long productId, Long userId);
}