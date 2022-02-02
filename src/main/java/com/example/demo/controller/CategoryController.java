package com.example.demo.controller;

import com.example.demo.domain.LargeCategory;
import com.example.demo.domain.SmallCategory;
import com.example.demo.domain.SmallestCategory;
import com.example.demo.domain.enums.Category;
import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.ResponseComDto;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.InvocationTargetException;

@Controller
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseComDto> createSingleCategory(CategoryDto.createSingle categoryDto) throws InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        categoryService.createSingleCategory(categoryDto);
        return new ResponseEntity<>(
                ResponseComDto.builder()
                        .resultMsg("카테고리 생성 완료")
                        .resultObj(null)
                        .build(), HttpStatus.OK);
    }

    @PostMapping("/create/all")
    public ResponseEntity<ResponseComDto> createAllCategory(CategoryDto.create categoryDto) throws InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        categoryService.createAllCategory(categoryDto);
        return new ResponseEntity<>(
                com.example.demo.dto.ResponseComDto.builder()
                        .resultMsg("카테고리 생성 완료")
                        .resultObj(null)
                        .build(), HttpStatus.OK);
    }
}
