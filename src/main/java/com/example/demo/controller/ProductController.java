package com.example.demo.controller;

import com.example.demo.domain.Product;
import com.example.demo.dto.ProductDto;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<?> searchProduct(){
        List<ProductDto> productDtoList = productService.showProduct();

        return ResponseEntity.ok(productDtoList);
    }
//    @PostMapping("/products/")
//    public ResponseEntity<?> createProduct(){
//
//    }
//    @PutMapping("/products/{id}")
//    public ResponseEntity<?> updateProduct(@PathVariable Long id){
//
//    }
//    @DeleteMapping("/products/{id}")
//    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
//
//    }
}
