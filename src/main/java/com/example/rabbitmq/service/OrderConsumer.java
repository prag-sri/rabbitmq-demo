package com.example.rabbitmq.service;

import com.example.rabbitmq.config.RabbitMQConfig;
import com.example.rabbitmq.model.Order;
import com.example.rabbitmq.repository.OrderRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {
    private final OrderRepository orderRepository;

    public OrderConsumer(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    void receiveOrder(Order order){
        System.out.println("Received Order: " + order);
        orderRepository.save(order);
    }
}
