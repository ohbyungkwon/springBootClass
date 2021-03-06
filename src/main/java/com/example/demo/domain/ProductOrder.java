package com.example.demo.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table
@EntityListeners(value = {AuditingEntityListener.class})
public class ProductOrder {
    @Id
    @GeneratedValue
    private Long seq;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "seq")
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "seq")
    private User user;

    @CreatedDate
    private Date createDate;

    @Column
    private int buyCnt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cashinfo_id", referencedColumnName = "seq")
    private CashInfo cashInfo;

    @Column
    private int usePoint;

    @Column
    private int totalPrice;
}
