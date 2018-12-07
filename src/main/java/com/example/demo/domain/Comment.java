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
    @Column
    @GeneratedValue
    private Long seq;

    private String content;

    @JoinColumn(name = "user_id", referencedColumnName = "seq")
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @JoinColumn(name = "question_id", referencedColumnName = "seq")
    @ManyToOne(cascade = CascadeType.ALL)
    private Question question;

    @CreatedDate
    private Date createDate;
}
