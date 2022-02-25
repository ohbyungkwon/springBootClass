//package com.example.demo.service;
//
//import com.example.demo.domain.Product;
//import com.example.demo.domain.ProductOrder;
//import com.example.demo.domain.User;
//import com.example.demo.domain.enums.Gender;
//import com.example.demo.domain.enums.PayMethod;
//import com.example.demo.dto.CashInfoDto;
//import com.example.demo.dto.OrderDto;
//import com.example.demo.dto.ProductDto;
//import com.example.demo.dto.UserDto;
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.persistence.EntityManager;
//
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.Date;
//
//import static org.junit.Assert.*;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class ProductOrderServiceTest {
//    @Autowired
//    private ProductOrderService productOrderService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @Test
//    @Rollback(value = false)
//    public void order(){
//        User recommendUser = this.getRecommendUser();
//        User user = this.getUser(recommendUser.getUsername());
//        Product product = this.getProduct();
//
//        CashInfoDto cashInfoDto = new CashInfoDto();
//        cashInfoDto.setInputter("testName");
//        cashInfoDto.setBankName("testBank");
//
//        OrderDto.Create orderDto = new OrderDto.Create();
//        orderDto.setProductId(product.getId());
//        orderDto.setBuyCnt(2);
//        orderDto.setPayMethod(PayMethod.BANKBOOK);
//        orderDto.setCashInfo(cashInfoDto);
//        orderDto.setUsePoint(500);
//        orderDto.setCity("Seoul");
//        orderDto.setStreet("Dorim");
//        orderDto.setZipcode("111111");
//
//        ProductOrder productOrder = productOrderService.saveOrder(orderDto, user.getUsername());
//
//        Assertions.assertThat(productOrder.getUser().getPoint()).isEqualTo(500);
//        Assertions.assertThat(productOrder.getUsePoint()).isEqualTo(500);
//        Assertions.assertThat(productOrder.getTotalPrice()).isEqualTo(19500);
//        Assertions.assertThat(productOrder.getProduct().getStockQuantity()).isEqualTo(998);
//        Assertions.assertThat(productOrder.getDelivery().getAddress().getCity()).isEqualTo("Seoul");
//    }
//
//    public User getUser(String recommendUsername){
//        UserDto.Create createDto = new UserDto.Create();
//        createDto.setUsername("obkTest");
//        createDto.setPassword("1234");
//        createDto.setName("obk");
//        createDto.setEmail("test@test.com");
//        createDto.setAddr("1");
//        createDto.setBirth("1");
//        createDto.setGender(Gender.MAN);
//        createDto.setRecommandUser(recommendUsername);
//
//        return userService.createUser(createDto);
//    }
//
//    public User getRecommendUser(){
//        UserDto.Create createDto = new UserDto.Create();
//        createDto.setUsername("obkTest1");
//        createDto.setPassword("1234");
//        createDto.setName("obk");
//        createDto.setEmail("test@test.com");
//        createDto.setAddr("1");
//        createDto.setBirth("1");
//        createDto.setGender(Gender.MAN);
//
//        return userService.createUser(createDto);
//    }
//
//    public Product getProduct(){
//        LocalDateTime expiredDate = LocalDateTime.of(2022, 12, 31, 0,0,0);
//
//        ProductDto.create productDto = new ProductDto.create();
//        productDto.setTitle("testProduct");
//        productDto.setImageUrl("test.jpg");
//        productDto.setMemo("memo test");
//        productDto.setPrice(10000);
//        productDto.setStockQuantity(1000);
//        productDto.setExpireDate(Date.from(expiredDate.atZone(ZoneId.systemDefault()).toInstant()));
//
//        return productService.createProduct(productDto);
//    }
//}