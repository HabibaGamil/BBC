package org.example.mqServer.RabbitMQ;

import org.example.mqServer.DataClasses.Request;
import org.example.mqServer.DataClasses.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@EnableAutoConfiguration
public class Producer {

    RabbitTemplate rabbitTemplate;
    @Autowired RabbitMQConfig rabbitMQConfig;

    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Value("${rabbitmq.exchange.mqServer.name}")
    private String exchange;


    public Response sendMessage(Request req,String app){

        LOGGER.info(String.format("api gateway request sent to RabbitMQ => %s", req.toString()));
        LOGGER.info(String.format("api gateway request sent to RabbitMQ => %s", rabbitMQConfig.getRoutingKeyMap()));
        LOGGER.info(String.format("app name", app ));
        String routingKey = rabbitMQConfig.getRoutingKeyMap().get(app);

        LOGGER.info(String.format("Routing key=> %s", routingKey));

        Response response = rabbitTemplate.convertSendAndReceiveAsType(exchange, routingKey, req, new ParameterizedTypeReference<>() {
        });
        LOGGER.info(String.format("Response", response));

        return response;

    }
}