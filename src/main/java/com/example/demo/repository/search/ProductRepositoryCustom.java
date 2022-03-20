package com.example.demo.repository.search;

import com.example.demo.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<ProductDto.showSimple> findProductWithCategory(ProductDto.searchOption searchOption, Pageable pageable);

    ProductDto.showDetail findProductDetail(Long productId);
}
