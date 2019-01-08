package com.example.demo.service;

import com.example.demo.domain.Product;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.elevenshop.ElevenProduct;
import com.example.demo.dto.elevenshop.ProductSerachResponse;
import com.example.demo.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ProductRepository productRepository;

    public List<ProductDto> showProduct(){
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();

        for(int i = 0; i < products.size(); i++){
            ProductDto productDto = ProductDto.builder()
                    .seq(products.get(i).getSeq())
                    .title(products.get(i).getTitle())
                    .price(products.get(i).getPrice())
                    .imageUrl(products.get(i).getImageUrl())
                    .build();
            productDtoList.add(productDto);
        }
        return productDtoList;
    }
}
