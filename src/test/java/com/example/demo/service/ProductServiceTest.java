//package com.example.demo.service;
//
//import com.example.demo.domain.Product;
//import com.example.demo.dto.ProductDto;
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.Date;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class ProductServiceTest {
//
//    @Autowired
//    private ProductService productService;
//
//    @Test
//    @Rollback(value = false)
//    public void createProduct(){
//        Product product = this.productBuilder();
//        Assertions.assertThat(product.getTitle()).isEqualTo("testProduct");
//    }
//
//    @Test
//    @Rollback(value = false)
//    public void updateProduct(){
//        Product product = this.productBuilder();
//
//        LocalDateTime expiredDate = LocalDateTime.of(2023, 1, 1, 0,0,0);
//
//        ProductDto.update productDto = new ProductDto.update();
//        productDto.setTitle("testProduct");
//        productDto.setImageUrl("test.jpg");
//        productDto.setMemo("memo test");
//        productDto.setPrice(10000);
//        productDto.setStockQuantity(1000);
//        productDto.setExpireDate(Date.from(expiredDate.atZone(ZoneId.systemDefault()).toInstant()));
//
//        productService.updateProduct(product, productDto);
//    }
//
//    public Product productBuilder(){
//        LocalDateTime expiredDate = LocalDateTime.of(2022, 12, 31, 0,0,0);
//
//        ProductDto.create productDto = new ProductDto.create();
//        productDto.setTitle("testProduct");
//        productDto.setImageUrl("test.jpg");
//        productDto.setMemo("memo test");
//        productDto.setPrice(10000);
//        productDto.setStockQuantity(1000);
//        productDto.setExpireDate(Date.from(expiredDate.atZone(ZoneId.systemDefault()).toInstant()));
//
//        return productService.createProduct(productDto);
//    }
//}