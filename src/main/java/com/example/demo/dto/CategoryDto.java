package com.example.demo.dto;

import com.example.demo.domain.LargeCategory;
import com.example.demo.domain.SmallCategory;
import com.example.demo.domain.SmallestCategory;
import com.example.demo.domain.enums.Category;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class CategoryDto {
    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class show {
        private Long id;
        private String title;

        public void convertDto(LargeCategory largeCategory, SmallCategory smallCategory,
                    SmallestCategory smallestCategory) {
            if(largeCategory != null){
                this.id = largeCategory.getId();
                this.title = largeCategory.getTitle();
            } else if(smallCategory != null){
                this.id = smallCategory.getId();
                this.title = smallCategory.getTitle();
            } else{
                this.id = smallestCategory.getId();
                this.title = smallestCategory.getTitle();
            }
        }
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class createSingle {
        private String title;

        @Enumerated(EnumType.STRING)
        private Category category;

        private Long largeCategoryId;

        private Long smallCategoryId;
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class create{
        private String smallestTitle;
        private String smallTitle;
        private String largeTitle;
    }
}
