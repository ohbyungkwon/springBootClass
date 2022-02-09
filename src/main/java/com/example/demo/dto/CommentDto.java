package com.example.demo.dto;

import lombok.*;

public class CommentDto {
    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class create {
        private String content;
        private Long questionId;
    }
}
