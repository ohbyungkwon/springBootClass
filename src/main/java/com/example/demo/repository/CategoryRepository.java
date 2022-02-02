package com.example.demo.repository;

import com.example.demo.domain.LargeCategory;
import com.example.demo.domain.SmallCategory;
import com.example.demo.domain.SmallestCategory;
import com.example.demo.domain.enums.Category;
import com.example.demo.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;

@Repository
public class CategoryRepository {
    private final EntityManager entityManager;

    @Autowired
    public CategoryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T> T findOne(Long id, Class<T> cls){
       return entityManager.find(cls, id);
    }

    public LargeCategory findLargeCategory(Long largeCategoryId){
        return entityManager.find(LargeCategory.class, largeCategoryId);
    }

    public SmallCategory findSmallCategory(Long smallCategoryId){
        return entityManager.find(SmallCategory.class, smallCategoryId);
    }

    public SmallestCategory findSmallestCategory(Long smallestCategoryId){
        return entityManager.find(SmallestCategory.class, smallestCategoryId);
    }

    public <T> T save(T obj) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        entityManager.persist(obj);
        return obj;
    }
}
