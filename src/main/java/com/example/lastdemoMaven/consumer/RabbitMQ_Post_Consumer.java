package com.example.lastdemoMaven.consumer;

import com.example.lastdemoMaven.dbo.PostEvent;
import com.example.lastdemoMaven.service.post_service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class RabbitMQ_Post_Consumer {

    private  post_service post_service;

    @RabbitListener(queues = "${rabbitmq.queue.post_queue}")
    public void consume(PostEvent event_comes_from_post){
        post_service.addToHashPosts(event_comes_from_post);
    }
}
