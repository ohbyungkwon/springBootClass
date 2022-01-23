package com.example.demo.dto;

import com.example.demo.domain.enums.PayMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class OrderDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create{
        private Long productId;
        private CashInfoDto cashInfo;
        private PayMethod payMethod;
        private int usePoint;
        private int buyCnt;
    }
}