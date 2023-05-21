package com.example.controller.RabbitMQ;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

@RefreshScope // important
@Configuration
@Component
@ConfigurationProperties(prefix = "controller")
@Getter
@Setter
@ToString
public class RabbitMQConfig {

    private Map<String, String> exchangeMap;


    // fanout exchange beans
    @Bean
    public FanoutExchange test_fanout() {
        return new FanoutExchange(exchangeMap.get("test"));
    }
    @Bean
    public FanoutExchange post_fanout() {
        return new FanoutExchange(exchangeMap.get("post"));
    }

    @Bean
    public FanoutExchange newsfeed_fanout() {
        return new FanoutExchange(exchangeMap.get("newsfeed"));
    }
    @Bean
    public FanoutExchange search_fanout() {
        return new FanoutExchange(exchangeMap.get("search"));
    }
    @Bean
    public FanoutExchange user_fanout() {
        return new FanoutExchange(exchangeMap.get("user"));
    }
    @Bean
    public FanoutExchange views_fanout() {
        return new FanoutExchange(exchangeMap.get("views"));
    }
    @Bean
    public FanoutExchange directory_fanout() {
        return new FanoutExchange(exchangeMap.get("directory"));
    }
    @Bean
    public FanoutExchange media_fanout() {
        return new FanoutExchange(exchangeMap.get("media"));
    }
    // message converter
    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    // configure RabbitTemplate
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}