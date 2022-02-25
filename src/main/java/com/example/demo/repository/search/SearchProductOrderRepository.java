package com.example.demo.repository.search;

import com.example.demo.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchProductOrderRepository {
    Page<OrderDto.showSimple> findMyOrders(Long userId, Pageable pageable);
    OrderDto.showDetail findOrderDetail(Long orderId);
}
