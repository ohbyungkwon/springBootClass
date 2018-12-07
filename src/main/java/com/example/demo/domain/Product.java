package com.example.demo.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
@EntityListeners(value = {AuditingEntityListener.class})
public class Product {
    @Id
    @Column
    @GeneratedValue
    private Long seq;

    @Column
    private String title;

    @Column
    private int price;

    @Column
    private String imageUrl;

    @Column
    private String optional;

    @Column
    @CreatedDate
    private Date createDate;

    @Column
    @LastModifiedDate
    private Date modifyDate;

    @Column
    private Date expireDate;

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private SmallestCategory smallestCategory;
}
