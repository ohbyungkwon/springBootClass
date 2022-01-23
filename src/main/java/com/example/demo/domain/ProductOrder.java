package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
public class ProductOrder {
    @Id
    @GeneratedValue
    private Long seq;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private CashInfo cashInfo;

    private int usePoint;

    private int buyCnt;

    private int totalPrice;

    @CreatedDate
    private Date createDate;
}
