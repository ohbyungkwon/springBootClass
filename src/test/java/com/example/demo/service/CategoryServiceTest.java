package com.example.demo.service;

import com.example.demo.domain.LargeCategory;
import com.example.demo.domain.SmallCategory;
import com.example.demo.domain.SmallestCategory;
import com.example.demo.domain.enums.Category;
import com.example.demo.dto.CategoryDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    @Rollback(value = false)
    public void createSingleCategory() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        CategoryDto.createSingle large = new CategoryDto.createSingle();
        large.setCategory(Category.LARGE);
        large.setTitle("의류");
        LargeCategory largeCategory = (LargeCategory) categoryService.createSingleCategory(large);

        CategoryDto.createSingle small = new CategoryDto.createSingle();
        small.setCategory(Category.SMALL);
        small.setTitle("코트");
        small.setLargeCategoryId(largeCategory.getId());
        SmallCategory smallCategory = (SmallCategory) categoryService.createSingleCategory(small);

        CategoryDto.createSingle smallest = new CategoryDto.createSingle();
        smallest.setCategory(Category.SMALLEST);
        smallest.setTitle("맥코트");
        smallest.setSmallCategoryId(smallCategory.getId());
        SmallestCategory smallestCategory = (SmallestCategory) categoryService.createSingleCategory(smallest);

        System.out.println("DONE");
    }

    @Test
    @Rollback(value = false)
    public void createAllCategory() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        CategoryDto.create create = new CategoryDto.create();
        create.setLargeTitle("의류");
        create.setSmallTitle("코드");
        create.setSmallestTitle("맥코드");
        categoryService.createAllCategory(create);
    }
}