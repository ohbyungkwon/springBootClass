package com.example.demo.domain;

import com.example.demo.domain.enums.Gender;
import com.example.demo.enums.AuthProvider;
import com.example.demo.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
public class User implements Serializable {
    @Column
    @Id
    @GeneratedValue
    private Long seq;

    @Column
    private String id;

    @Column
    private String pw;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String birth;

    @Column
    private String addr;

    @Column
    private Gender gender;

    @Column
    private int point;

    @Column
    @CreatedDate
    private Date registerDate;

    @Column
    @LastModifiedDate
    private Date modifyDate;

    @Column
    private Date lastLogined;

    @Column
    private Boolean isEnable;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Role role;//권한 하나만 가지고있음

    @Column
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column
    private String accessToken;

    @Column
    private String refreshToken;
}
