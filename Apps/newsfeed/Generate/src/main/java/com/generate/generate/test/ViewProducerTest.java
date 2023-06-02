package com.generate.generate.test;

import com.generate.generate.dbo.cassandra.ViewRequest;
import com.generate.generate.dbo.redis.PostEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service

@RequiredArgsConstructor
@Slf4j
public class ViewProducerTest {
    private final RabbitTemplate rabbitTemplate;

    public ViewRequest publishView(){
        ViewRequest viewRequest = createViewRequest();
        rabbitTemplate.convertAndSend("my_queue", viewRequest);
        return viewRequest;
    }

    private ViewRequest createViewRequest(){
        String userId = String.valueOf((int)(Math.random()*100));
        String categoryId = String.valueOf((int)(1 + Math.random()*5));
        ViewRequest viewRequest = new ViewRequest();
        viewRequest.setUserId(userId);
        viewRequest.setCategoryId(categoryId);
        return viewRequest;
    }
}
