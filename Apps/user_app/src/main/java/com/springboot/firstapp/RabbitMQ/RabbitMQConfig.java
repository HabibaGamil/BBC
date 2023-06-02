package com.springboot.firstapp.RabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.controllerServer.name}")
    private String exchange;
    
    
    @Value("${rabbitmq.user_newsfeed_queue.queue_name}")
    private String QUEUE_STRING ;
    
    @Value("${rabbitmq.user_newsfeed_queue.exchange_name}")
    private String EXCHANGE_STRING;
    
    @Value("${rabbitmq.user_newsfeed_queue.routing_key}")
    private String ROUTING_KEY_STRING = "youssef";
	

	@Bean
	public Queue queue() {
		return new Queue(QUEUE_STRING);
	}
	
	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(EXCHANGE_STRING);
	}
	
	@Bean
	public Binding binding2(Queue queue, TopicExchange topicExchange) {
		return BindingBuilder.bind(queue).to(topicExchange).with(ROUTING_KEY_STRING);
	}
    
    
    
    
    @Bean
    public Queue controllerQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange(exchange);
    }

    // spring bean for binding between exchange and queue using routing key
    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(controllerQueue())
                .to(fanout());
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