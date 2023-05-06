package com.example.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
public class Sender {
    private final RabbitTemplate rabbitTemplate;

    public Sender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {

        rabbitTemplate.convertAndSend("test", message);
    }

    public void sendClass(Class updatedClass) {
        rabbitTemplate.convertAndSend("test", updatedClass);
    }
}