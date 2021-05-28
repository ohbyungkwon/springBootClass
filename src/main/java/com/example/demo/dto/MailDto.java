package com.example.demo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class MailDto {
    @Data
    public static class SendEmail{
        @Email
        @NotEmpty(message = "email null")
        private String email;
    }

    @Data
    public static class AuthEmail{
        @Length(min = 6, max = 6)
        @NotEmpty(message = "code null")
        private String code;
    }
}
