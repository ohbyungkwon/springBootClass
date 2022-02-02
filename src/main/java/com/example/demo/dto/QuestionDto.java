package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class QuestionDto {
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class create {
        private String content;
        private Long productId;
    }
}
