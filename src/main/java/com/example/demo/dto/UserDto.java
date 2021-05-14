package com.example.demo.dto;

import com.example.demo.domain.User;
import com.example.demo.domain.enums.Gender;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserDto {
    @Data
    public static class Create {
        @NotEmpty(message = "id null")
        @Length(min=4, max = 8)
        private String id;

        @NotEmpty(message = "pwd null")
        @Length(min = 6)
        private String pwd;

        @NotEmpty(message = "name null")
        private String name;

        @NotEmpty(message = "email null")
        @Email
        private String email;

        @NotEmpty(message = "birth null")
        @Length(max = 6)
        private String birth;

        @NotEmpty(message = "addr null")
        private String addr;

        private Gender gender;

        private String recommandUser;
    }

    @Data
    public static class Update{
        private String addr;

        private Boolean isEnable;
    }

    @Data
    public static class UpdatePassword{
        @NotEmpty(message = "pwd null")
        @Length(min = 4, max = 8)
        private String pwd;
    }
}
