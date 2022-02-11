package com.example.demo.domain;

import com.example.demo.domain.enums.Category;
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

    private int stockQuantity;

    private String imageUrl;

    private String memo;

    private Date expireDate;

    @CreatedDate
    private Date createDate;

    @CreatedBy
    private String createUsername;

    @LastModifiedDate
    private Date modifyDate;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private SmallestCategory smallestCategory;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private SmallCategory smallCategory;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private LargeCategory largeCategory;

    public static Product create(ProductDto.create productDto) {
        return Product.builder()
                .title(productDto.getTitle())
                .stockQuantity(productDto.getStockQuantity())
                .price(productDto.getPrice())
                .imageUrl(productDto.getImageUrl())
                .memo(productDto.getMemo())
                .expireDate(productDto.getExpireDate())
                .build();
    }

    public Product update(ProductDto.update productDto) {
        this.title = productDto.getTitle();
        this.price = productDto.getPrice();
        this.stockQuantity = productDto.getStockQuantity();
        this.imageUrl = productDto.getImageUrl();
        this.memo = productDto.getMemo();
        this.expireDate = productDto.getExpireDate();
        return this;
    }

    public void minusStockQuantity(int buyCnt) {
        this.stockQuantity -= buyCnt;
    }

    public void joinCategory(LargeCategory largeCategory, SmallCategory smallCategory,
                             SmallestCategory smallestCategory) {
        this.smallestCategory = smallestCategory;
        this.smallCategory = smallCategory;
        this.largeCategory = largeCategory;

        smallestCategory.getProduct().add(this);
        smallCategory.getProduct().add(this);
        largeCategory.getProduct().add(this);
    }


    public ProductDto.show convertDto(Category category) {
        ProductDto.show temp = ProductDto.show.builder()
                .id(this.id)
                .title(this.title)
                .price(this.price)
                .imageUrl(this.imageUrl)
                .optional(this.memo)
                .build();

        if (category == Category.LARGE) {
            temp.getLargeCategory().setId(this.largeCategory.getId());
            temp.getLargeCategory().setTitle(this.largeCategory.getTitle());
        } else if (category == Category.SMALL){
            temp.getSmallCategory().setId(this.smallCategory.getId());
            temp.getSmallCategory().setTitle(this.smallCategory.getTitle());
        } else if(category == Category.SMALLEST){
            temp.getSmallestCategory().setId(this.smallestCategory.getId());
            temp.getSmallestCategory().setTitle(this.smallestCategory.getTitle());
        } else{
            temp.getLargeCategory().setId(this.largeCategory.getId());
            temp.getLargeCategory().setTitle(this.largeCategory.getTitle());
            temp.getSmallCategory().setId(this.smallCategory.getId());
            temp.getSmallCategory().setTitle(this.smallCategory.getTitle());
            temp.getSmallestCategory().setId(this.smallestCategory.getId());
            temp.getSmallestCategory().setTitle(this.smallestCategory.getTitle());
        }

        return temp;
    }
}
