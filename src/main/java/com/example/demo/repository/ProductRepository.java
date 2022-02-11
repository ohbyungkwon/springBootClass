package com.example.demo.repository;

import com.example.demo.domain.Product;
import com.example.demo.repository.search.SearchProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, SearchProductRepository {
    @Override
    Optional<Product> findById(Long aLong);
}