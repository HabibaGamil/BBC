package com.example.controller.RabbitMQ;

import com.example.controller.ControllerCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
public class ControllerProducer {
    private Logger LOGGER = LoggerFactory.getLogger(ControllerProducer.class);
    @Value("${rabbitmq.exchange.controller.name}")
    private String exchange;
    @Value("${rabbitmq.binding.routing.key}")
    private String controllerRoutingKey;

    private RabbitTemplate rabbitTemplate;

    public ControllerProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(ControllerCommand controllerCommand){
        LOGGER.info(String.format("Controller command to RabbitMQ => %s", controllerCommand.toString()));

        // send a command to test app controller queue
        RequestResponse rr = rabbitTemplate.convertSendAndReceiveAsType(exchange, controllerRoutingKey, controllerCommand, new ParameterizedTypeReference<>() {
        });
        LOGGER.info(String.format("Controller command to RabbitMQ => %s", rr.toString()));

    }
}