package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.model.OrderEntity;
import com.example.orderservice.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderDto createOdrer(OrderDto orderDto) {

        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getQty()* orderDto.getUnitPrice());

        OrderEntity orderEntity = modelMapper.map(orderDto,OrderEntity.class);

        orderEntity = orderRepository.save(orderEntity);
        OrderDto returnOrderDto = modelMapper.map(orderEntity, OrderDto.class);

        return returnOrderDto;
    }

    @Override
    public OrderDto getOdrerByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        OrderDto returnOrderDto = modelMapper.map(orderEntity, OrderDto.class);
        return returnOrderDto;
    }

    @Override
    public Iterable<OrderEntity> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }
}
