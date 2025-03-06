package com.example.rabbitmq.controller;

import com.example.rabbitmq.model.Order;
import com.example.rabbitmq.service.OrderProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer){
        this.orderProducer = orderProducer;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order){
        orderProducer.sendOrder("order.queue", order);
        return new ResponseEntity<>("Order created successfully!", HttpStatus.CREATED);
    }
}
