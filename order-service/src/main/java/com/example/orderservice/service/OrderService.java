package com.example.orderservice.service;


import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.model.OrderEntity;

public interface OrderService {

    OrderDto createOdrer(OrderDto orderDto);
    OrderDto getOdrerByOrderId(String orderId);
    Iterable<OrderEntity> getOrdersByUserId(String userId);

}
