package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.domain.enums.Category;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.QuestionDto;
import com.example.demo.enums.Role;
import com.example.demo.exception.BadClientException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.common.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final QuestionService questionService;
    private final CommonService commonService;
    private final UserRepository userRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryService categoryService,
                          QuestionService questionService, CommonService commonService,
                          UserRepository userRepository){
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.questionService = questionService;
        this.commonService = commonService;
        this.userRepository = userRepository;
    }

    public Page<Product> searchProducts(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    public Page<ProductDto.showSimple> searchProductsUsingOption(ProductDto.searchOption searchOption, Pageable pageable){
        return productRepository.findProductWithCategory(searchOption, pageable);
    }

    public ProductDto.showDetail searchProductsDetail(Long productId){
        List<QuestionDto.showSimple> questions = questionService.showQuestionsInProduct(productId);

        ProductDto.showDetail productDetail = productRepository.findProductDetail(productId);
        productDetail.setQuestions(questions);

        return productDetail;
    }

    @Transactional
    public Product createProduct(ProductDto.create productDto, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadClientException("사용자 정보가 없습니다."));
        if(!user.getRole().equals(Role.ADMIN_ROLE)){
            throw new BadClientException("관리자 계정만 상품 등록 가능합니다");
        }

        Product product = Product.create(productDto);
        LargeCategory largeCategory = categoryService.findCategory(productDto.getLargeCategoryId(), LargeCategory.class);
        SmallCategory smallCategory = categoryService.findCategory(productDto.getSmallCategoryId(), SmallCategory.class);
        SmallestCategory smallestCategory = categoryService.findCategory(productDto.getSmallestCategoryId(), SmallestCategory.class);

        product.joinCategory(largeCategory, smallCategory, smallestCategory);

        return productRepository.save(product);
    }

    @Transactional
    public ProductDto.update updateProduct(Product product, ProductDto.update productDto, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadClientException("사용자 정보가 없습니다."));
        if(!user.getRole().equals(Role.ADMIN_ROLE)){
            throw new BadClientException("관리자 계정만 상품 등록 가능합니다");
        }

        if(!product.getCreateUsername().equals(username)){
            throw new BadClientException("본인이 등록한 상품이 아닙니다.");
        }

        Date expiredDate = product.getExpireDate();
        if(commonService.isOverTargetDate(expiredDate)){
            throw new BadClientException("이미 만료된 상품은 수정 불가합니다.");
        }

        product.update(productDto);
        return productDto;
    }

    @Transactional
    public void deleteProduct(Product product){
        productRepository.delete(product);
    }
}
