package com.example.payments.configuration.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfiguration {
    @Value("${alert-queue.name.sender-queue}")
    private String senderQueue;
    @Value("${alert-queue.name.receiver-queue}")
    private String receiverQueue;
    @Value("${alert-queue.exchange}")
    private String exchange;
    @Value("${alert-queue.routing-key.sender-key}")
    private String senderRoutingKey;
    @Value("${alert-queue.routing-key.receiver-key}")
    private String receiverRoutingKey;
    @Bean
    public Queue senderQueue() {
        return new Queue(senderQueue);
    }
    @Bean
    public Queue receiverQueue() {
        return new Queue(receiverQueue);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    // using routing key
    @Bean
    public Binding senderBinding() {
        return BindingBuilder.bind(senderQueue())
                .to(exchange())
                .with(senderRoutingKey);
    }

    @Bean
    public Binding receiverBinding() {
        return BindingBuilder.bind(receiverQueue())
                .to(exchange())
                .with(receiverRoutingKey);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
