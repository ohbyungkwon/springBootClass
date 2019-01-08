package com.example.demo.job;

import com.example.demo.domain.Product;
import com.example.demo.dto.elevenshop.ElevenProduct;
import org.springframework.batch.item.ItemProcessor;

public class ProductItemProcessor implements ItemProcessor<ElevenProduct, Product> {

    @Override
    public Product process(ElevenProduct item) throws Exception {
        Product product = com.example.demo.domain.Product.builder()
                    .imageUrl(item.getProductImage())
                    .price(item.getSalePrice())
                    .title(item.getProductName())
                    .build();

        return product;
    }
}
