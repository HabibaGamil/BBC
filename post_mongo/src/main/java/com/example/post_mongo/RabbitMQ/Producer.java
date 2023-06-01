/*
package com.example.post_mongo.RabbitMQ;

import com.example.test_app.DataClasses.Request;
import com.example.test_app.DataClasses.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;


@Service
public class Producer {

    RabbitTemplate rabbitTemplate;
    @Autowired
    RabbitMQConfig rabbitMQConfig;
    private Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Autowired
    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    @Value("${rabbitmq.exchange.newsfeed_post.name}")
    private String exchange;

    @Value("${post-mongo.routingKeyMap.newsfeed_post}")
    private String newsfeed_post_routingKey;

    public void sendMessage(Request req){

        LOGGER.info(String.format("api gateway request sent to RabbitMQ => %s", req.toString()));
        LOGGER.info(String.format("api gateway request sent to RabbitMQ => %s", newsfeed_post_routingKey));

        rabbitTemplate.convertAndSend(exchange, newsfeed_post_routingKey, req);

    }
}*/
