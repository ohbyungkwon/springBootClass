package com.example.demo.repository;

import com.example.demo.domain.ProductOrder;
import com.example.demo.repository.search.ProductOrderRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long>, ProductOrderRepositoryCustom {
    Boolean existsByProductIdAndUserId(Long productId, Long userId);
}