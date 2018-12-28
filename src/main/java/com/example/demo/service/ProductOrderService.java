package com.example.demo.service;

import com.example.demo.domain.CashInfo;
import com.example.demo.domain.Product;
import com.example.demo.domain.ProductOrder;
import com.example.demo.domain.User;
import com.example.demo.domain.enums.CashInfoState;
import com.example.demo.dto.OrderDto;
import com.example.demo.exception.BadClientException;
import com.example.demo.repository.ProductOrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProductOrderService {
    private ProductOrderRepository productOrderRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    @Autowired
    public ProductOrderService(ProductOrderRepository productOrderRepository,
                               ProductRepository productRepository,
                               UserRepository userRepository){
        this.productOrderRepository = productOrderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ProductOrder saveOrder(OrderDto.Create dto, String loginedUser){
        Product product = productRepository.findProductById(dto.getSeqProduct());

        User user = userRepository.findById(loginedUser);

        if(dto.getUsePoint() > user.getPoint()){
            throw new BadClientException("포인트 부족");
        }
        if(System.currentTimeMillis() > product.getExpireDate().getTime()){
            throw new BadClientException("상품 만료");
        }

        int totalPrice = dto.getBuyCnt() * product.getPrice() - dto.getUsePoint();
        user.setPoint(user.getPoint() - dto.getUsePoint());

        Object paymentResponse = null;

        if(paymentResponse != null) {
            throw new BadClientException("카드 에러");
        }

        CashInfo cashInfo = CashInfo.builder()
                .bankname(dto.getCashInfo().getBankname())
                .inputter(dto.getCashInfo().getInputter())
                .methodTitle(dto.getPayMethod())
                .state(CashInfoState.SUCCESS)
                .build();

        ProductOrder productOrder = ProductOrder.builder()
                                .buyCnt(dto.getBuyCnt())
                                .cashInfo(cashInfo)
                                .totalPrice(totalPrice)
                                .usePoint(dto.getUsePoint())
                                .user(user)
                                .product(product)
                                .build();

        return productOrderRepository.save(productOrder);
    }
}
