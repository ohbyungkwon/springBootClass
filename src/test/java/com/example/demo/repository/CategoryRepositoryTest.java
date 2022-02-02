//package com.example.demo.repository;
//
//import com.example.demo.domain.LargeCategory;
//import com.example.demo.domain.SmallCategory;
//import com.example.demo.domain.SmallestCategory;
//import com.example.demo.domain.enums.Category;
//import com.example.demo.dto.CategoryDto;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//
//import java.lang.reflect.InvocationTargetException;
//
//import static org.junit.Assert.*;
//
//@Transactional
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class CategoryRepositoryTest {
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @Test
//    @Rollback(value = false)
//    public void createSingleCategory() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
//        CategoryDto.createSingle large = new CategoryDto.createSingle();
//        large.setCategory(Category.LARGE);
//        large.setTitle("의류");
//        LargeCategory largeCategory = categoryRepository.createSingleCategory(large, LargeCategory.class);
//
//        CategoryDto.createSingle small = new CategoryDto.createSingle();
//        small.setCategory(Category.SMALL);
//        small.setTitle("코트");
//        small.setLargeCategoryId(largeCategory.getId());
//        SmallCategory smallCategory = categoryRepository.createSingleCategory(small, SmallCategory.class);
//
//        CategoryDto.createSingle smallest = new CategoryDto.createSingle();
//        smallest.setCategory(Category.SMALLEST);
//        smallest.setTitle("맥코트");
//        smallest.setSmallCategoryId(smallCategory.getId());
//        SmallestCategory smallestCategory = categoryRepository.createSingleCategory(smallest, SmallestCategory.class);
//
//        System.out.println("DONE");
//    }
//
//    @Test
//    @Rollback(value = false)
//    public void create(){
//        SmallestCategory smallestCategory = new SmallestCategory();
//        smallestCategory.setTitle("맥코드");
//
//        SmallCategory smallCategory = new SmallCategory();
//        smallCategory.setTitle("코드");
//        smallCategory.getSmallestCategory().add(smallestCategory);
//        smallestCategory.setSmallCategory(smallCategory);
//
//        LargeCategory largeCategory = new LargeCategory();
//        largeCategory.setTitle("의휴");
//        largeCategory.getSmallCategory().add(smallCategory);
//        smallCategory.setLargeCategory(largeCategory);
//
//        entityManager.persist(largeCategory);
//    }
//}