package com.example.controller.RabbitMQ;

import com.example.controller.ControllerCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
public class ControllerProducer {
    private Logger LOGGER = LoggerFactory.getLogger(ControllerProducer.class);
    @Autowired
    RabbitMQConfig rabbitMQConfig;

    private RabbitTemplate rabbitTemplate;

    public ControllerProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(ControllerCommand controllerCommand, String app){
        LOGGER.info(String.format("Controller command to RabbitMQ => %s", controllerCommand.toString()));

        String exchange = rabbitMQConfig.getExchangeMap().get(app);

        LOGGER.info(String.format("Routing key => %s", exchange));

        // send a command to test app controller queue
        try {
            rabbitTemplate.convertAndSend(exchange, "", controllerCommand);
        }
        catch (Exception ex)
        {
            LOGGER.info(ex.getMessage());

        }
    }
}