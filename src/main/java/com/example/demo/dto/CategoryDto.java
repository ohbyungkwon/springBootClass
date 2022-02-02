package com.example.demo.dto;

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
    public static class createSingle {
        String title;

        @Enumerated(EnumType.STRING)
        Category category;

        Long largeCategoryId;

        Long smallCategoryId;
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class create{
        String smallestTitle;
        String smallTitle;
        String largeTitle;
    }
}
