package com.example.directory_app.RabbitMQ;

import com.example.directory_app.DataClasses.SearchRequest;
import com.example.directory_app.DataClasses.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
@EnableAutoConfiguration
public class Producer {

    RabbitTemplate rabbitTemplate;
    @Autowired RabbitMQConfig rabbitMQConfig;

    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Value("newsfeed_dir_exchange")
    private String exchange;


    public SearchResponse sendMessage(SearchRequest req){

        LOGGER.info(String.format("api gateway request sent to RabbitMQ => %s", req.toString()));
        LOGGER.info(String.format("api gateway request sent to RabbitMQ => %s", rabbitMQConfig.getRoutingKeyMap()));

        String routingKey = "newsfeed_dir_routing_key";

        LOGGER.info(String.format("Routing key=> %s", routingKey));

        SearchResponse response = rabbitTemplate
                .convertSendAndReceiveAsType(exchange, routingKey, req, new ParameterizedTypeReference<>() {});

        LOGGER.info(String.format("Response", response));

        return response;

    }
}