package com.example.demo;

import com.example.demo.dto.elevenshop.ProductSerachResponse;
import com.example.demo.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired private ProductService productService;

    @Test
    public void contextLoads() {
        productService.getProducts();
    }

}
