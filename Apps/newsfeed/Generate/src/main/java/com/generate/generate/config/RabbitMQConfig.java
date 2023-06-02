package com.generate.generate.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    /* Topic exchange routes messages to one or many queues based on matching
    between a message routing key and the pattern that was used to bind
    a queue to an exchange */


    @Value("${rabbitmq.queue.newsfeed.search}")
    private String queue_name;

    @Value("${rabbitmq.exchange.newsfeed.search}")
    private String exchange_name;

    @Value("${rabbitmq.routing.key}")
    private String routing_key;


    @Bean
    public Queue queue(){
        return new Queue(queue_name);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange_name);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(exchange()).with(routing_key);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
//
//    // configure RabbitTemplate
//    @Bean
//    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(converter());
//        return rabbitTemplate;
//    }
}
