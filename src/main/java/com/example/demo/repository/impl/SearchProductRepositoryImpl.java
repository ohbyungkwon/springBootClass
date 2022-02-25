package com.example.demo.repository.impl;

import com.example.demo.domain.Product;
import static com.example.demo.domain.QProduct.product;
import static com.example.demo.domain.QLargeCategory.largeCategory;
import static com.example.demo.domain.QSmallCategory.smallCategory;
import static com.example.demo.domain.QSmallestCategory.smallestCategory;

import com.example.demo.domain.QLoginHistory;
import com.example.demo.domain.enums.Category;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.QProductDto_showDetail;
import com.example.demo.dto.QProductDto_showSimple;
import com.example.demo.repository.search.SearchProductRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
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
     */
    @Override
    public Page<ProductDto.showSimple> findProductWithCategory(ProductDto.searchOption searchOption, Pageable pageable){
        JPAQuery<ProductDto.showSimple> selectFrom = jpaQueryFactory
                .select(
                        new QProductDto_showSimple(product)
                )
                .from(product);
        if(searchOption.getCategory() == Category.LARGE){
            selectFrom = joinLargeCategory(selectFrom, false);
        } else if(searchOption.getCategory() == Category.SMALL){
            selectFrom = joinSmallCategory(selectFrom, false);
        } else if(searchOption.getCategory() == Category.SMALLEST){
            selectFrom = joinSmallestCategory(selectFrom, false);
        } else {
            selectFrom = joinLargeCategory(selectFrom, false);
            selectFrom = joinSmallCategory(selectFrom, false);
            selectFrom = joinSmallestCategory(selectFrom, false);
        }

        QueryResults<ProductDto.showSimple> result = selectFrom
                .where(titleEquals(searchOption.getProductTitle()),
                        categoryIdEquals(searchOption.getCategory(), searchOption.getCategoryId()))
                .orderBy(product.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<ProductDto.showSimple> list = result.getResults();
        long totalCnt = result.getTotal();

        return new PageImpl<ProductDto.showSimple>(list, pageable, totalCnt);
    }

    @Override
    public ProductDto.showDetail findProductDetail(Long productId) {
        JPAQuery<ProductDto.showDetail> selectFrom = jpaQueryFactory
                .select(
                        new QProductDto_showDetail(product)
                )
                .from(product);

        selectFrom = joinLargeCategory(selectFrom, true);
        selectFrom = joinSmallCategory(selectFrom, true);
        selectFrom = joinSmallestCategory(selectFrom, true);

        return selectFrom
                .where(productIdEquals(productId))
                .fetchOne();
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

    private BooleanExpression productIdEquals(Long productId) {
        return product.id.eq(productId);
    }

    public <T> JPAQuery<T> joinLargeCategory(JPAQuery<T> selectFrom, boolean isFetch){
        return (isFetch) ? selectFrom.innerJoin(product.largeCategory, largeCategory).fetchJoin()
                : selectFrom.join(product.largeCategory, largeCategory);
    }

    public <T> JPAQuery<T> joinSmallCategory(JPAQuery<T> selectFrom, boolean isFetch){
        return (isFetch) ? selectFrom.innerJoin(product.smallCategory, smallCategory).fetchJoin()
                : selectFrom.join(product.smallCategory, smallCategory);
    }

    public <T> JPAQuery<T> joinSmallestCategory(JPAQuery<T> selectFrom, boolean isFetch){
        return (isFetch) ? selectFrom.innerJoin(product.smallestCategory, smallestCategory).fetchJoin()
                : selectFrom.join(product.smallestCategory, smallestCategory);
    }
}
