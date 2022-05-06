package com.example.orderservice.controller;


import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.RequestOrder;
import com.example.orderservice.model.OrderEntity;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.ResponseOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/order-service")
public class OrderController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    OrderService orderService;

    @GetMapping("/check")
    public String check(){
        return "check world in order service " ;
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createUser(
            @PathVariable("userId") String userId,
            @RequestBody RequestOrder orderDetail){

        OrderDto orderDto = modelMapper.map(orderDetail, OrderDto.class);
        orderDto.setUserId(userId);
        OrderDto createOrder = orderService.createOdrer(orderDto);

        ResponseOrder responseOrder = modelMapper.map(createOrder,ResponseOrder.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> createUser(
            @PathVariable("userId") String userId){
        Iterable<OrderEntity> orderEntities = orderService.getOrdersByUserId(userId);
        List<ResponseOrder> result = new ArrayList<>();
        orderEntities.forEach(v-> result.add(modelMapper.map(v, ResponseOrder.class)));

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
