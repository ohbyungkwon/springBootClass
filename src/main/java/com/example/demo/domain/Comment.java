package com.example.demo.domain;

import com.example.demo.dto.CommentDto;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table
@Builder
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

    public void joinQuestion(Question question){
        this.question = question;
        question.getComments().add(this);
    }

    public static Comment commentBuilder(CommentDto.create commentDto,
                                         Question question, User user){
        return Comment.builder()
                .content(commentDto.getContent())
                .question(question)
                .user(user)
                .build();
    }
}
