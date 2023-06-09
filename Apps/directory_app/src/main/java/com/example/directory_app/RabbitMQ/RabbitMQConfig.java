package com.example.directory_app.RabbitMQ;

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
@ConfigurationProperties(prefix = "directoryapp")
@Getter @Setter @ToString
public class RabbitMQConfig {

    private Map<String, String> routingKeyMap;
    private Map<String, String> queueMap;


    @Value("newsfeed_dir_exchange")
    private String newsfeed_dir_exchange;

    @Value("view_count_exchange")
    private String view_dir_exchange;


    ///////////////////////// Queues Beans /////////////////
    @Bean
    public Queue directoryApp_newsfeedDirAppQueue(){
        return new Queue("newsfeed_dir_queue");
    }

    @Bean
    public Queue viewsDirQueue(){
        return new Queue("view_count_dir_queue");
    }

    // spring bean for exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(newsfeed_dir_exchange);
    }



    // spring bean for binding between exchange and queue using routing key //
    @Bean
    public Binding newsFeedAppBinding(){
        return BindingBuilder
                .bind(directoryApp_newsfeedDirAppQueue())
                .to(exchange())
                .with("newsfeed_dir_routing_key");
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