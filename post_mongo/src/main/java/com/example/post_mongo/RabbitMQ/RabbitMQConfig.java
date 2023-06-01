package com.example.post_mongo.RabbitMQ;

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
@ConfigurationProperties(prefix = "post-mongo")
@Getter @Setter @ToString
public class RabbitMQConfig {

    private Map<String, String> routingKeyMap;
    private Map<String, String> queueMap;


    @Value("postApp_exchange")
    private String exchange;

    ///////////////////////// Queues Beans /////////////////
    @Bean
    public Queue mqServer_newsfeedPostQueue(){
        return new Queue("mqServer_post_queue");
    }

    // spring bean for exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding newsfeedPostBinding(){
        return BindingBuilder
                .bind(mqServer_newsfeedPostQueue())
                .to(exchange())
                .with(routingKeyMap.get("mqServer_post_routingKey"));
    }

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