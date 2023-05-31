package com.example.views.RabbitMQ;

import com.example.views.DataClasses.ViewsBroadcastRequest;
import com.example.views.ViewService;
import com.example.views.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableAutoConfiguration
public class Producer {
    RabbitTemplate rabbitTemplate;
    @Autowired
    RabbitMQConfig rabbitMQConfig;

    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Value("view_count_exchange")
    private String broadcast_views_exchange;

    @Autowired
    private ViewService viewService;

    public void broadcastCurrentViewsCount() {

        List<Views> viewsList = viewService.getAllViews();

        ViewsBroadcastRequest viewsBroadcastRequest =
                ViewsBroadcastRequest.builder()
                        .views(viewsList)
                        .build();

        //TODO: change log messages
        LOGGER.info(String.format("api gateway request sent to RabbitMQ => %s", viewsBroadcastRequest.toString()));
        LOGGER.info(String.format("api gateway request sent to RabbitMQ => %s", rabbitMQConfig.getRoutingKeyMap()));

        String routingKey = "view_count_routing_key";

        LOGGER.info(String.format("Routing key=> %s", routingKey));

        rabbitTemplate.convertAndSend(broadcast_views_exchange, routingKey, viewsBroadcastRequest);

        viewService.deleteAllViews();

        LOGGER.info("views records successfully sent to queue");
    }
}