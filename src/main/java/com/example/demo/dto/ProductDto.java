package com.example.demo.dto;

import com.example.demo.domain.Product;
import com.example.demo.domain.Question;
import com.example.demo.domain.enums.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDto {

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class searchOption {
        private String productTitle;
        private Category category;
        private Long categoryId;
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class showSimple {
        private Long id;
        private String title;
        private int price;
        private String imageUrl;

        @QueryProjection
        public showSimple(Product product){
            this.id = product.getId();
            this.title = product.getTitle();
            this.price = product.getPrice();
            this.imageUrl = product.getImageUrl();
        }
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class showDetail {
        private String title;
        private int price;
        private int stockQuantity;
        private String imageUrl;
        private String optional;
        private Date expireDate;
        private Date createDate;
        private String createUsername;
        private Date modifyDate;
        private CategoryDto.show largeCategory;
        private CategoryDto.show smallCategory;
        private CategoryDto.show smallestCategory;
        private List<QuestionDto.showSimple> questions; //Comment는 Question 상세 보기 이용

        @QueryProjection
        public showDetail(Product product){
            this.title = product.getTitle();
            this.price = product.getPrice();
            this.imageUrl = product.getImageUrl();
            this.optional = product.getMemo();
            this.stockQuantity = product.getStockQuantity();
            this.expireDate = product.getExpireDate();
            this.createDate = product.getCreateDate();
            this.createUsername = product.getCreateUsername();
            this.modifyDate = product.getModifyDate();

            this.largeCategory.convertDto(product.getLargeCategory(), null, null);
            this.smallCategory.convertDto(null, product.getSmallCategory(), null);
            this.smallestCategory.convertDto(null, null, product.getSmallestCategory());
        }
    }

    @Setter
    @Getter
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

    @Setter
    @Getter
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