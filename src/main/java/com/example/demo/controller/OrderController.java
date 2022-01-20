package com.example.demo.controller;

import com.example.demo.dto.OrderDto;
import com.example.demo.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
public class OrderController extends AbstractController{
    private ProductOrderService productOrderService;

    @Autowired
    public OrderController(ProductOrderService productOrderService){
        this.productOrderService = productOrderService;
    }


    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto.Create dto, Principal principal){
        productOrderService.saveOrder(dto, getUsername(principal).getUsername());

        return ResponseEntity.ok().build();
    }

}
