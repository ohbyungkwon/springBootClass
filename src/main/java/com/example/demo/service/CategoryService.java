package com.example.demo.service;

import com.example.demo.domain.LargeCategory;
import com.example.demo.domain.SmallCategory;
import com.example.demo.domain.SmallestCategory;
import com.example.demo.domain.enums.Category;
import com.example.demo.dto.CategoryDto;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;

@Service
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Object createSingleCategory(CategoryDto.createSingle categoryDto) throws InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        String title = categoryDto.getTitle();
        Category category = categoryDto.getCategory();
        if(category == Category.LARGE){
            LargeCategory largeCategory = LargeCategory.create(title);
            return categoryRepository.save(largeCategory);
        } else if(category == Category.SMALL){
            LargeCategory largeCategory = categoryRepository.findOne(categoryDto.getLargeCategoryId(), LargeCategory.class);
            SmallCategory smallCategory = SmallCategory.create(title);
            smallCategory.joinLargeCategory(largeCategory);
            return categoryRepository.save(smallCategory);
        } else{
            SmallCategory smallCategory = categoryRepository.findOne(categoryDto.getSmallCategoryId(), SmallCategory.class);
            SmallestCategory smallestCategory = SmallestCategory.create(title);
            smallestCategory.joinSmallCategory(smallCategory);
            return categoryRepository.save(smallestCategory);
        }
    }

    @Transactional
    public void createAllCategory(CategoryDto.create categoryDto) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        SmallestCategory smallestCategory = SmallestCategory.create(categoryDto.getSmallestTitle());
        SmallCategory smallCategory = SmallCategory.create(categoryDto.getSmallTitle());
        LargeCategory largeCategory = LargeCategory.create(categoryDto.getLargeTitle());

        smallestCategory.joinSmallCategory(smallCategory);
        smallCategory.joinLargeCategory(largeCategory);

        categoryRepository.save(largeCategory);
    }

    public <T> T findCategory(Long id, Class<T> cls){
        return categoryRepository.findOne(id, cls);
    }
}
