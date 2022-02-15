package com.example.demo.repository.impl;

import com.example.demo.domain.Product;
import static com.example.demo.domain.QProduct.product;
import static com.example.demo.domain.QLargeCategory.largeCategory;
import static com.example.demo.domain.QSmallCategory.smallCategory;
import static com.example.demo.domain.QSmallestCategory.smallestCategory;

import com.example.demo.domain.QLoginHistory;
import com.example.demo.domain.enums.Category;
import com.example.demo.dto.ProductDto;
import com.example.demo.repository.search.SearchProductRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SearchProductRepositoryImpl implements SearchProductRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public SearchProductRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    /**
     * 상품 검색
     * 1. ALL - 전체검색
     * 2. 특정 Category 내에서 상품 검색
     * 3. 특정 Category 내 상품 리스트 검색
     * @param productTitle
     * @param category
     * @param categoryId
     * @param pageable
     * @return
     */
    @Override
    public Page<ProductDto.show> findProductWithCategory(String productTitle, Category category,
                                                            Long categoryId, Pageable pageable){
        JPAQuery<Product> selectFrom = jpaQueryFactory.selectFrom(product);
        if(category == Category.LARGE){
            selectFrom = joinAllLargeCategory(selectFrom);
        } else if(category == Category.SMALL){
            selectFrom = joinAllSmallCategory(selectFrom);
        } else if(category == Category.SMALLEST){
            selectFrom = joinAllSmallestCategory(selectFrom);
        } else {
            selectFrom = joinAllLargeCategory(selectFrom);
            selectFrom = joinAllSmallCategory(selectFrom);
            selectFrom = joinAllSmallestCategory(selectFrom);
        }

        QueryResults<Product> result = selectFrom
                .where(titleEquals(productTitle),
                        categoryIdEquals(category, categoryId))
                .orderBy(product.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ProductDto.show> list = result.getResults().stream()
                .map(o->o.convertDto(category))
                .collect(Collectors.toList());
        long totalCnt = result.getTotal();

        return new PageImpl<ProductDto.show>(list, pageable, totalCnt);
    }

    private BooleanExpression titleEquals(String keyword) {
        return keyword == null ? null : product.title.containsIgnoreCase(keyword);
    }

    private BooleanExpression categoryIdEquals(Category category, Long id) {
        if(category == Category.LARGE){
            return product.largeCategory.id.eq(id);
        } else if(category == Category.SMALL){
            return product.smallCategory.id.eq(id);
        } else if(category == Category.SMALLEST){
            return product.smallestCategory.id.eq(id);
        } else{
            return null;
        }
    }

    public JPAQuery<Product> joinAllLargeCategory(JPAQuery<Product> selectFrom){
        return selectFrom.innerJoin(product.largeCategory, largeCategory).fetchJoin();
    }

    public JPAQuery<Product> joinAllSmallCategory(JPAQuery<Product> selectFrom){
        return selectFrom.innerJoin(product.smallCategory, smallCategory).fetchJoin();
    }

    public JPAQuery<Product> joinAllSmallestCategory(JPAQuery<Product> selectFrom){
        return selectFrom.innerJoin(product.smallestCategory, smallestCategory).fetchJoin();
    }
}
