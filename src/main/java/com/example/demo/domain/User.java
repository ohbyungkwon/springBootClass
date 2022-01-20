package com.example.demo.domain;

import com.example.demo.domain.enums.Gender;
import com.example.demo.dto.UserDto;
import com.example.demo.enums.AuthProvider;
import com.example.demo.enums.Role;
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
import java.util.Optional;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
public class User implements Serializable {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String birth;

    @Column
    private String addr;

    @Column
    @Enumerated(EnumType.STRING)
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
    private Date lastLoginedDate;

    @Column
    private Boolean isEnable;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;//권한 하나만 가지고있음

    @Column
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column
    private String accessToken;

    @Column
    private String refreshToken;

    public static User create(UserDto.Create userDto, String password){
        Role role = Optional.ofNullable(userDto.getRole()).orElse(Role.UNAUTHORIZATION_ROLE);

        return User.builder()
                .username(userDto.getUsername())
                .password(password)
                .name(userDto.getName())
                .email(userDto.getEmail())
                .birth(userDto.getBirth())
                .addr(userDto.getAddr())
                .gender(userDto.getGender())
                .provider(AuthProvider.local)
                .role(role)
                .build();
    }

    public void updatePassword(String password){
        this.password = password;
    }

    public void recommend(User recommendedUser){
        int newPoint = 1000;

        this.point = newPoint;
        recommendedUser.setPoint(recommendedUser.getPoint() + newPoint);
    }
}
