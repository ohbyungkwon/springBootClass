package com.example.demo.domain;

import com.example.demo.domain.enums.DeliveryState;
import com.example.demo.dto.OrderDto;
import com.example.demo.exception.BadClientException;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
public class ProductOrder {
    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CashInfo cashInfo;

    @JoinColumn
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Delivery delivery;

    private int buyCnt; //촐 구매 개수

    private int usePoint; //포인트 사용 금액

    private int totalPrice; //포인트 제외 결제 금액

    private DeliveryState deliveryState;

    @CreatedDate
    private Date createDate;

    @CreatedBy
    private String createUser;

    public static ProductOrder create(CashInfo cashInfo, User user,
                               Product product, OrderDto.Create orderDto){
        if(orderDto.getUsePoint() > user.getPoint()){
            throw new BadClientException("포인트가 부족합니다.");
        }

        if(System.currentTimeMillis() > product.getExpireDate().getTime()){
            throw new BadClientException("상품이 만료되었습니다.");
        }

        if(orderDto.getBuyCnt() > product.getStockQuantity()){
            throw new BadClientException("상품 수량이 부족합니다.");
        }

        Delivery delivery = Delivery.create(orderDto);

        product.minusStockQuantity(orderDto.getBuyCnt());
        user.minusPoint(orderDto.getUsePoint());
        int totalPrice = (orderDto.getBuyCnt() * product.getPrice()) - orderDto.getUsePoint();

        return ProductOrder.builder()
                .cashInfo(cashInfo)
                .totalPrice(totalPrice)
                .usePoint(orderDto.getUsePoint())
                .buyCnt(orderDto.getBuyCnt())
                .user(user)
                .product(product)
                .delivery(delivery)
                .deliveryState(DeliveryState.READY)
                .build();
    }
}
