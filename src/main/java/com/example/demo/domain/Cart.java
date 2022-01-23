package com.example.demo.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table
@EntityListeners(value = {AuditingEntityListener.class})
public class Cart {
    @Column
    @Id
    @GeneratedValue
    private Long seq;

    @Column
    private int productNum;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private User user;

    @CreatedDate
    private Date createDate;
}
