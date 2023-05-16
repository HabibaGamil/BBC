package org.example.mqServer.RabbitMQ;

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
@ConfigurationProperties(prefix = "mqserver")
@Getter @Setter @ToString
public class RabbitMQConfig {

    private Map<String, String> routingKeyMap;
    private Map<String, String> queueMap;


    @Value("${rabbitmq.exchange.mqServer.name}")
    private String exchange;

    ///////////////////////// Queues Beans /////////////////
    @Bean
    public Queue mqServer_testAppQueue(){
        return new Queue(queueMap.get("testapp"));
    }
    @Bean
    public Queue mqServer_postsQueue(){
        return new Queue(queueMap.get("posts"));
    }
    @Bean
    public Queue mqServer_newsfeedQueue(){
        return new Queue(queueMap.get("newsfeed"));
    }
    @Bean
    public Queue mqServer_searchQueue(){
        return new Queue(queueMap.get("search"));
    }
    @Bean
    public Queue mqServer_userQueue() {
        return new Queue(queueMap.get("user"));
    }
    @Bean
    public Queue mqServer_viewsQueue() {
        return new Queue(queueMap.get("views"));
    }
    @Bean
    public Queue mqServer_directoryQueue() {
        return new Queue(queueMap.get("directory"));
    }
    @Bean
    public Queue mqServer_mediaQueue() {
        return new Queue(queueMap.get("media"));
    }

    // spring bean for exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    // spring bean for binding between exchange and queue using routing key //
    @Bean
    public Binding testAppBinding(){
        return BindingBuilder
                .bind(mqServer_testAppQueue())
                .to(exchange())
                .with(routingKeyMap.get("testapp"));
    }
    @Bean
    public Binding postsBinding(){
        return BindingBuilder
                .bind(mqServer_postsQueue())
                .to(exchange())
                .with(routingKeyMap.get("posts"));
    }
    @Bean
    public Binding newsfeedBinding(){
        return BindingBuilder
                .bind(mqServer_newsfeedQueue())
                .to(exchange())
                .with(routingKeyMap.get("newsfeed"));
    }
    @Bean
    public Binding searchBinding(){
        return BindingBuilder
                .bind(mqServer_searchQueue())
                .to(exchange())
                .with(routingKeyMap.get("search"));
    }
    @Bean
    public Binding userBinding(){
        return BindingBuilder
                .bind(mqServer_userQueue())
                .to(exchange())
                .with(routingKeyMap.get("user"));
    }
    @Bean
    public Binding viewsBinding(){
        return BindingBuilder
                .bind(mqServer_viewsQueue())
                .to(exchange())
                .with(routingKeyMap.get("views"));
    }
    @Bean
    public Binding directoryBinding(){
        return BindingBuilder
                .bind(mqServer_directoryQueue())
                .to(exchange())
                .with(routingKeyMap.get("directory"));
    }
    @Bean
    public Binding mediaBinding(){
        return BindingBuilder
                .bind(mqServer_mediaQueue())
                .to(exchange())
                .with(routingKeyMap.get("media"));
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