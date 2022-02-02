package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public class ProductDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class show {
        private Long id;

        private String title;

        private int price;

        private String imageUrl;

        private String optional;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class create {
        private Long id;

        private String title;

        private int price;

        private int stockQuantity;

        private String imageUrl;

        private String memo;

        private Date expireDate;

        private Long smallestCategoryId;

        private Long smallCategoryId;

        private Long largeCategoryId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class update {
        private String title;

        private int price;

        private int stockQuantity;

        private String imageUrl;

        private String memo;

        private Date expireDate;
    }
}