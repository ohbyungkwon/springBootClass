package com.example.demo.service;

import com.example.demo.annotation.CustTransaction;
import com.example.demo.domain.CashInfo;
import com.example.demo.domain.Product;
import com.example.demo.domain.ProductOrder;
import com.example.demo.domain.User;
import com.example.demo.dto.OrderDto;
import com.example.demo.exception.BadClientException;
import com.example.demo.repository.ProductOrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductOrderService {
    private final ProductOrderRepository productOrderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProductOrderService(ProductOrderRepository productOrderRepository,
                               ProductRepository productRepository,
                               UserRepository userRepository){
        this.productOrderRepository = productOrderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ProductOrder saveOrder(OrderDto.Create orderDto, String username){
        Product product = productRepository.findById(orderDto.getProductId())
                .orElseThrow(() -> new BadClientException("상품이 존재하지 않습니다."));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadClientException("사용자 정보가 존재하지 않습니다."));

        CashInfo cashInfo = CashInfo.create(orderDto);

        ProductOrder productOrder = ProductOrder.create(cashInfo, user, product, orderDto);

        return productOrderRepository.save(productOrder);
    }
}
