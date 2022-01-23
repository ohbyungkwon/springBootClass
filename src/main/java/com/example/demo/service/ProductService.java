package com.example.demo.service;

import com.example.demo.domain.Product;
import com.example.demo.dto.ProductDto;
import com.example.demo.exception.BadClientException;
import com.example.demo.repository.ProductRepository;
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
    private final CommonService commonService;

    @Autowired
    public ProductService(ProductRepository productRepository, CommonService commonService){
        this.productRepository = productRepository;
        this.commonService = commonService;
    }

    public Page<Product> showProduct(Pageable pageable){
        Page<Product> products = productRepository.findAll(pageable);
        log.info("product-page: {}", products);

        return products;
    }

    @Transactional
    public Product createProduct(ProductDto.create productDto){
        Product product = Product.create(productDto);
        return productRepository.save(product);
    }

    @Transactional
    public void updateProduct(Product product, ProductDto.update productDto){
        Date expiredDate = product.getExpireDate();
        if(commonService.isOverTargetDate(expiredDate)){
            throw new BadClientException("이미 만료된 상품은 수정 불가합니다.");
        }

        product.update(productDto);
    }

    @Transactional
    public void deleteProduct(Product product){
        productRepository.delete(product);
    }
}
