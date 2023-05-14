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

import java.util.Map;


@RefreshScope // important
@Configuration
@ConfigurationProperties(prefix = "mqserver")
@Getter @Setter @ToString
public class RabbitMQConfig {

    private Map<String, String> routingKeyMap;
    private Map<String, String> queueMap;
    //private Map<String, String> exchangeMap;

    @Value("${rabbitmq.queue.mqServer_testApp.name}")
    private String mqServer_testAppQueue;

    @Value("${rabbitmq.exchange.mqServer.name}")
    private String exchange;

    @Value("${rabbitmq.binding.mqServer_testApp.routing.key}")
    private String mqServer_testAppRoutingKey;


    // spring bean for queue - testApp queue
    @Bean
    public Queue mqServer_testAppQueue(){
        return new Queue(queueMap.get("testApp"));
    }

    // spring bean for exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    // spring bean for binding between exchange and queue using routing key
    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(mqServer_testAppQueue())
                .to(exchange())
                .with(routingKeyMap.get("testApp"));
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