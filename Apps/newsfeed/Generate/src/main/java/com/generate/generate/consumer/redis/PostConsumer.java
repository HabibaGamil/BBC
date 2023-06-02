package com.generate.generate.consumer.redis;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.generate.generate.service.redis.post_service;
import com.generate.generate.dbo.redis.PostEvent;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostConsumer {

    private  final post_service post_service;

    @RabbitListener(queues = "${rabbitmq.queue.post_queue}")
    public void consume(PostEvent event_comes_from_post){
        log.info(event_comes_from_post.toString());
        post_service.addToHashPosts(event_comes_from_post);
    }
}
