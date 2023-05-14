package com.example.test_app.RabbitMQ;


import com.example.test_app.DataClasses.Request;
import com.example.test_app.DataClasses.Response;
import com.example.test_app.controller.ControllerCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = "${rabbitmq.queue.controller.name}")
    public Response consume(Request controllerCommand){
        LOGGER.info(String.format("Controller command received in test_app service => %s", controllerCommand.toString()));
        return new Response("The controller command is received to test app, this is the test app response.");
    }

    @RabbitListener(queues = "${rabbitmq.queue.mqServer.name}")
    public Response consumeMQServerMessage(Request request){

        LOGGER.info(String.format("Request Received from MQ server => %s", request.toString()));
        return new Response("The MQ server Request is received to test app, this is the test app response.");
    }
}