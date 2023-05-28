package com.example.test_app.RabbitMQ;

import com.example.test_app.DataClasses.Request;
import com.example.test_app.DataClasses.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;


public class Producer {

    RabbitTemplate rabbitTemplate;
    @Autowired
    RabbitMQConfig rabbitMQConfig;
    private Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    @Value("${rabbitmq.exchange.testapp.name}")
    private String exchange;

    @Value("${rabbitmq.queue.testApp_testApp2.name}")
    private String testApp2RoutingKey;

    public Response sendMessage(Request req){

        LOGGER.info(String.format("api gateway request sent to RabbitMQ => %s", req.toString()));
        LOGGER.info(String.format("api gateway request sent to RabbitMQ => %s", testApp2RoutingKey));

        Response response = rabbitTemplate.convertSendAndReceiveAsType(exchange, testApp2RoutingKey, req, new ParameterizedTypeReference<>() {
        });
        LOGGER.info(String.format("Response", response));

        return response;

    }
}
