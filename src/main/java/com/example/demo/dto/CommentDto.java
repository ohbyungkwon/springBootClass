package com.example.demo.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.Date;

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

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    public static class show {
        private String content;
        private Date createdDate;
        private String username;

        @QueryProjection
        public show(String content, Date createdDate, String username) {
            this.content = content;
            this.createdDate = createdDate;
            this.username = username;
        }
    }
}
