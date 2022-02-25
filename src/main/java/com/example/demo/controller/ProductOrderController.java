package com.example.demo.controller;

import com.example.demo.dto.OrderDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.ResponseComDto;
import com.example.demo.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class ProductOrderController {
    private final ProductOrderService productOrderService;

    @Autowired
    public ProductOrderController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<ResponseComDto> create(OrderDto.Create orderDto, Principal principal) {
        productOrderService.saveOrder(orderDto, principal.getName());
        return new ResponseEntity<>(
                ResponseComDto.builder()
                        .resultMsg("주문 완료")
                        .resultObj(null)
                        .build(), HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<ResponseComDto> searchMyProductOrder(Principal principal, Pageable pageable) {
        String username = principal.getName();
        Page<OrderDto.showSimple> myOrders = productOrderService.searchMyProductOrder(username, pageable);

        return new ResponseEntity<>(
                ResponseComDto.builder()
                        .resultMsg("주문 리스트 조회 완료")
                        .resultObj(myOrders)
                        .build(), HttpStatus.OK);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ResponseComDto> searchMyProductOrder(@PathVariable Long orderId) {
        OrderDto.showDetail myOrder = productOrderService.searchMyProductOrderDetail(orderId);

        return new ResponseEntity<>(
                ResponseComDto.builder()
                        .resultMsg("주문 리스트 조회 완료")
                        .resultObj(myOrder)
                        .build(), HttpStatus.OK);
    }
}