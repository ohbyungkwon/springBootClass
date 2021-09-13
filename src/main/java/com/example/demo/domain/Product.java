package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
@Builder
@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor
@AllArgsConstructor
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
    private String memo;

    @Column
    @CreatedDate
    private Date createDate;

    @Column
    @LastModifiedDate
    private Date modifyDate;

    @Column
    private Date expireDate;

    @ManyToOne
    private SmallestCategory smallestCategory;
}
