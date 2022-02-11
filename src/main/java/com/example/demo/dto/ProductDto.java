package com.example.demo.dto;

import com.example.demo.domain.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

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

        private CategoryDto.show largeCategory;

        private CategoryDto.show smallCategory;

        private CategoryDto.show smallestCategory;
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