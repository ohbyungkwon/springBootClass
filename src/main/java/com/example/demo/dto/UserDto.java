package com.example.demo.dto;

import com.example.demo.domain.enums.Gender;
import com.example.demo.enums.Role;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserDto {
    @Data
    public static class Create {
        @Length(min=4, max = 8)
        @NotEmpty(message = "id null")
        private String id;

        @Length(min = 6)
        @NotEmpty(message = "pwd null")
        private String pwd;

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

        private Gender gender;

        private String recommandUser;

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
        private String pw;
    }
}
