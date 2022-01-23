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
    private Long id;

    private String username;

    private String password;

    private String name;

    private String email;

    private String birth;

    private String addr;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int point;

    @CreatedDate
    private Date registerDate;

    @LastModifiedDate
    private Date modifyDate;

    private Date lastLoginedDate;

    private Boolean isEnable;

    @Enumerated(EnumType.STRING)
    private Role role;//권한 하나만 가지고있음

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String accessToken;

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
