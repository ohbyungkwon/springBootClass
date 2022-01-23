package com.example.demo.controller;

import com.example.demo.domain.ProductOrder;
import com.example.demo.dto.OrderDto;
import com.example.demo.dto.ResponseComDto;
import com.example.demo.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
public class OrderController extends AbstractController{
    private final ProductOrderService productOrderService;

    @Autowired
    public OrderController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<ResponseComDto> createOrder(@RequestBody OrderDto.Create orderDto, Principal principal){
        ProductOrder productOrder = productOrderService.saveOrder(orderDto, principal.getName());

        return new ResponseEntity<>(
                ResponseComDto.builder()
                        .resultMsg("주문 완료되었습니다.")
                        .resultObj(orderDto)
                        .build(), HttpStatus.OK);
    }
}
