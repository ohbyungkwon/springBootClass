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
public class Question {
    @Column
    @Id
    @GeneratedValue
    private Long seq;

    @Column
    private String content;

    @CreatedDate
    @Column
    private Date createDate;

    @JoinColumn
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @JoinColumn
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

    @Column
    private boolean buy;

    @Column
    private boolean deleted;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Comment> comment;
}
//category 등등 추가하기 모델