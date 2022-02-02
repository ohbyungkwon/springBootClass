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
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @CreatedDate
    private Date createDate;
}
