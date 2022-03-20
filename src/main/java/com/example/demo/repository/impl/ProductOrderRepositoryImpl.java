package com.example.demo.repository.impl;

import static com.example.demo.domain.QProductOrder.productOrder;
import static com.example.demo.domain.QProduct.product;
import static com.example.demo.domain.QUser.user;
import static com.example.demo.domain.QCashInfo.cashInfo;
import static com.example.demo.domain.QDelivery.delivery;

import com.example.demo.domain.ProductOrder;
import com.example.demo.dto.OrderDto;
import com.example.demo.dto.QOrderDto_showDetail;
import com.example.demo.dto.QOrderDto_showSimple;
import com.example.demo.repository.search.ProductOrderRepositoryCustom;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductOrderRepositoryImpl implements ProductOrderRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public ProductOrderRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<OrderDto.showSimple> findMyOrders(Long userId, Pageable pageable) {
        List<OrderDto.showSimple> myOrders = jpaQueryFactory
                .select(
                        new QOrderDto_showSimple(
                                productOrder.product.title,
                                productOrder.product.price,
                                productOrder.buyCnt,
                                productOrder.usePoint,
                                productOrder.totalPrice,
                                productOrder.deliveryState
                        )
                )
                .from(productOrder)
                .innerJoin(productOrder.user, user)
                .innerJoin(productOrder.product, product).fetchJoin()
                .where(productOrder.user.id.eq(userId))
                .orderBy(productOrder.createDate.desc())
                .fetch();

        JPAQuery<ProductOrder> myOrderCnt = jpaQueryFactory
                .selectFrom(productOrder)
                .innerJoin(productOrder.user, user)
                .where(productOrder.user.id.eq(userId));

        return PageableExecutionUtils.getPage(myOrders, pageable, myOrderCnt::fetchCount);
    }

    @Override
    public OrderDto.showDetail findOrderDetail(Long orderId) {
        return jpaQueryFactory
                .select(
                    new QOrderDto_showDetail(
                            productOrder.product.title,
                            productOrder.product.price,
                            productOrder.product.imageUrl,
                            productOrder.buyCnt,
                            productOrder.usePoint,
                            productOrder.totalPrice,
                            productOrder.deliveryState,
                            productOrder.createDate,
                            productOrder.cashInfo.bankName,
                            productOrder.cashInfo.inputter,
                            productOrder.cashInfo.payMethod,
                            productOrder.cashInfo.state,
                            productOrder.delivery.address.city,
                            productOrder.delivery.address.street,
                            productOrder.delivery.address.zipcode
                            )
                )
                .from(productOrder)
                .innerJoin(productOrder.product, product).fetchJoin()
                .innerJoin(productOrder.cashInfo, cashInfo).fetchJoin()
                .innerJoin(productOrder.delivery, delivery).fetchJoin()
                .where(productOrder.id.eq(orderId))
                .fetchOne();
    }
}
