package com.example.demo.dto;

import com.example.demo.domain.embedded.Address;
import com.example.demo.domain.enums.CashInfoState;
import com.example.demo.domain.enums.DeliveryState;
import com.example.demo.domain.enums.PayMethod;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import javax.persistence.Embedded;
import java.util.Date;

public class OrderDto {
    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create{
        private Long productId;
        private CashInfoDto cashInfo;
        private PayMethod payMethod;
        private int usePoint;
        private int buyCnt;
        private String city;
        private String street;
        private String zipcode;
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    public static class showSimple {
        private String productTitle;
        private int productPrice;
        private int buyCnt;
        private int usePoint;
        private int totalPrice;
        private DeliveryState deliveryState;

        @QueryProjection
        public showSimple(String productTitle, int productPrice, int buyCnt, int usePoint, int totalPrice,
                          DeliveryState deliveryState) {
            this.productTitle = productTitle;
            this.productPrice = productPrice;
            this.buyCnt = buyCnt;
            this.usePoint = usePoint;
            this.totalPrice = totalPrice;
            this.deliveryState = deliveryState;
        }
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    public static class showDetail {
        private String productTitle;
        private int productPrice;
        private String productImageUrl;
        private int buyCnt;
        private int usePoint;
        private int totalPrice;
        private DeliveryState deliveryState;
        private Date createDate;
        private String bankName;
        private String inputter;
        private PayMethod payMethod;
        private CashInfoState cashInfoState;
        private String city;
        private String street;
        private String zipcode;

        @QueryProjection
        public showDetail(String productTitle, int productPrice, String productImageUrl,
                          int buyCnt, int usePoint, int totalPrice, DeliveryState deliveryState,
                          Date createDate, String bankName, String inputter, PayMethod payMethod,
                          CashInfoState cashInfoState, String city, String street, String zipcode) {
            this.productTitle = productTitle;
            this.productPrice = productPrice;
            this.productImageUrl = productImageUrl;
            this.buyCnt = buyCnt;
            this.usePoint = usePoint;
            this.totalPrice = totalPrice;
            this.deliveryState = deliveryState;
            this.createDate = createDate;
            this.bankName = bankName;
            this.inputter = inputter;
            this.payMethod = payMethod;
            this.cashInfoState = cashInfoState;
            this.city = city;
            this.street = street;
            this.zipcode = zipcode;
        }
    }
}