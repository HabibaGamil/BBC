package com.example.test_app.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;



@Component
public class Receiver {
    // private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
    @RabbitListener(queues = "test")
    public void receiveMessage(String message) {
        System.out.println("I got a message!!!");
    }
}