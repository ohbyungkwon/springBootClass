package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.domain.enums.Gender;
import com.example.demo.domain.enums.PayMethod;
import com.example.demo.dto.*;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuestionServiceTest {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Test
    @Rollback(value = false)
    public void createQuestion() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        order();

        QuestionDto.create questionDto = new QuestionDto.create();
        questionDto.setContent("testContent");
        questionDto.setProductId(6L);

        questionService.createQuestion(questionDto, "obkTest");
    }

    public void order() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        User recommendUser = this.getRecommendUser();
        User user = this.getUser(recommendUser.getUsername());
        Product product = this.getProduct();

        CashInfoDto cashInfoDto = new CashInfoDto();
        cashInfoDto.setInputter("testName");
        cashInfoDto.setBankName("testBank");

        OrderDto.Create orderDto = new OrderDto.Create();
        orderDto.setProductId(product.getId());
        orderDto.setBuyCnt(2);
        orderDto.setPayMethod(PayMethod.BANKBOOK);
        orderDto.setCashInfo(cashInfoDto);
        orderDto.setUsePoint(500);
        orderDto.setCity("Seoul");
        orderDto.setStreet("Dorim");
        orderDto.setZipcode("111111");

        productOrderService.saveOrder(orderDto, user.getUsername());
    }

    public User getUser(String recommendUsername){
        UserDto.Create createDto = new UserDto.Create();
        createDto.setUsername("obkTest");
        createDto.setPassword("1234");
        createDto.setName("obk");
        createDto.setEmail("test@test.com");
        createDto.setAddr("1");
        createDto.setBirth("1");
        createDto.setGender(Gender.MAN);
        createDto.setRecommandUser(recommendUsername);

        return userService.createUser(createDto);
    }

    public User getRecommendUser(){
        UserDto.Create createDto = new UserDto.Create();
        createDto.setUsername("obkTest1");
        createDto.setPassword("1234");
        createDto.setName("obk");
        createDto.setEmail("test@test.com");
        createDto.setAddr("1");
        createDto.setBirth("1");
        createDto.setGender(Gender.MAN);

        return userService.createUser(createDto);
    }

    public Product getProduct() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        createAllCategory();

        LocalDateTime expiredDate = LocalDateTime.of(2022, 12, 31, 0,0,0);

        ProductDto.create productDto = new ProductDto.create();
        productDto.setTitle("testProduct");
        productDto.setImageUrl("test.jpg");
        productDto.setMemo("memo test");
        productDto.setPrice(10000);
        productDto.setStockQuantity(1000);
        productDto.setExpireDate(Date.from(expiredDate.atZone(ZoneId.systemDefault()).toInstant()));
        productDto.setLargeCategoryId(3L);
        productDto.setSmallCategoryId(4L);
        productDto.setSmallestCategoryId(5L);

        return productService.createProduct(productDto);
    }

    public void createAllCategory() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        CategoryDto.create create = new CategoryDto.create();
        create.setLargeTitle("의류");
        create.setSmallTitle("코드");
        create.setSmallestTitle("맥코드");
        categoryService.createAllCategory(create);
    }
}