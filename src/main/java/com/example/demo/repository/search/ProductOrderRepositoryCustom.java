package com.example.demo.repository.search;

import com.example.demo.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductOrderRepositoryCustom {
    Page<OrderDto.showSimple> findMyOrders(Long userId, Pageable pageable);
    OrderDto.showDetail findOrderDetail(Long orderId);
}
