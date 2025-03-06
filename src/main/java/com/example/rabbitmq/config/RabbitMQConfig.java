package com.example.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Defines the queue name where messages will be stored.
    public static final String QUEUE_NAME = "order.queue";

    // Defines the exchange that routes messages.
    public static final String EXCHANGE_NAME = "order.exchange";

    // Defines the key that links the exchange to the queue.
    public static final String ROUTING_KEY = "order.routingKey";


    // This method creates a queue named "order.queue".
    @Bean
    public Queue orderQueue(){
        return new Queue(QUEUE_NAME, true);  // true means the queue is durable (it persists even after RabbitMQ restarts).
    }


    // This method creates a Direct Exchange named "order.exchange".
    @Bean
    public DirectExchange orderExchange(){
        return new DirectExchange(EXCHANGE_NAME);
    }


    // This binds the order.queue to order.exchange with the routing key "order.routingKey".
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    // Converts Java objects to JSON before sending them to RabbitMQ.
    // Converts received JSON messages back to Java objects.
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // RabbitTemplate is used to send messages to RabbitMQ.
    // It injects the connection factory and sets the JSON message converter.
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }
}
