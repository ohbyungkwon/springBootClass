package com.example.demo.dto;

import com.example.demo.domain.enums.Gender;
import com.example.demo.enums.Role;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserDto {
    @Data
    public static class Create {
        @Length(min=4, max = 8)
        @NotEmpty(message = "username null")
        private String username;

        @Length(min = 6)
        @NotEmpty(message = "password null")
        private String password;

        @NotEmpty(message = "name null")
        private String name;

        @Email
        @NotEmpty(message = "email null")
        private String email;

        @Length(max = 6)
        @NotEmpty(message = "birth null")
        private String birth;

        @NotEmpty(message = "addr null")
        private String addr;

        @Enumerated(EnumType.STRING)
        private Gender gender;

        private String recommandUser;

        @Enumerated(EnumType.STRING)
        private Role role;
    }

    @Data
    public static class Update{
        private String addr;

        private Boolean isEnable;
    }

    @Data
    public static class UpdatePassword{
        @NotEmpty(message = "pw null")
        @Length(min = 4, max = 8)
        private String password;
    }
}
