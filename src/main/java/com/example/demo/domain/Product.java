package com.example.demo.domain;

import com.example.demo.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private int price;

    private String imageUrl;

    private String memo;

    @CreatedDate
    private Date createDate;

    @CreatedBy
    private String createUsername;

    @LastModifiedDate
    private Date modifyDate;

    private Date expireDate;

    @ManyToOne
    @JoinColumn
    private SmallestCategory smallestCategory;

    public static Product create(ProductDto.create productDto){
        return Product.builder()
                .title(productDto.getTitle())
                .price(productDto.getPrice())
                .imageUrl(productDto.getImageUrl())
                .memo(productDto.getMemo())
                .expireDate(productDto.getExpireDate())
                .build();
    }

    public Product update(ProductDto.update productDto){
        this.title = productDto.getTitle();
        this.price = productDto.getPrice();
        this.imageUrl = productDto.getImageUrl();
        this.memo = productDto.getMemo();
        this.expireDate = productDto.getExpireDate();
        return this;
    }
}
