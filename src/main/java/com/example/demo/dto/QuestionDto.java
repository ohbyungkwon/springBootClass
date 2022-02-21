package com.example.demo.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.Date;

public class QuestionDto {
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class create {
        private String content;
        private Long productId;
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    public static class show {
        private String questionContent;
        private Date questionCreateDate;
        private Date questionModifiedDate;
        private String productImageUrl;
        private String productTitle;

        @QueryProjection

        public show(String questionContent, Date questionCreateDate, Date questionModifiedDate,
                    String productImageUrl, String productTitle) {
            this.questionContent = questionContent;
            this.questionCreateDate = questionCreateDate;
            this.questionModifiedDate = questionModifiedDate;
            this.productImageUrl = productImageUrl;
            this.productTitle = productTitle;
        }
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    public static class showDetail {
        private String questionContent;
        private Date questionCreateDate;
        private Date questionModifiedDate;
        private String productImageUrl;
        private String productTitle;
        private String commentContent;
        private Date commentCreateDate;

        @QueryProjection
        public showDetail(String questionContent, Date questionCreateDate, Date questionModifiedDate,
                          String productImageUrl, String productTitle, String commentContent, Date commentCreateDate) {
            this.questionContent = questionContent;
            this.questionCreateDate = questionCreateDate;
            this.questionModifiedDate = questionModifiedDate;
            this.productImageUrl = productImageUrl;
            this.productTitle = productTitle;
            this.commentContent = commentContent;
            this.commentCreateDate = commentCreateDate;
        }
    }
}
