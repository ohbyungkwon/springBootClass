package com.example.demo.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
public class LoginHistory implements Serializable {
    @Column
    @Id
    @GeneratedValue
    private Long seq;

    @Column
    private String username;

    @Column
    private Boolean isSuccess;

    @Column
    @CreatedDate
    private Date accessTime;
}
