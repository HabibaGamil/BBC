package com.generate.generate.test;

import com.generate.generate.dbo.cassandra.SubscriptionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

@RequiredArgsConstructor
@Slf4j
public class SubscriptionProducerTest {

    private final RabbitTemplate rabbitTemplate;

    private String userId;

    private List<String> topicsId;

    private List<String> topicsName;

    public SubscriptionRequest publishSubscription(){
        SubscriptionRequest subscriptionRequest = createSubscriptionRequest();
        rabbitTemplate.convertAndSend("my_queue2", subscriptionRequest);
        return subscriptionRequest;
    }



    private SubscriptionRequest createSubscriptionRequest(){
        String userId = String.valueOf((int)(Math.random()*100));
        int len = (int)(1 + Math.random()*5);
        List<String> topicsId = new ArrayList<>();
        List<String> topicsName = new ArrayList<>();
        for(int i = 0 ; i<len;i++){
            String id = String.valueOf((int)(1+Math.random()*5));
            char c = (char)('a' + (int)(Math.random()*26));
            String name = c +""+ c;
            topicsId.add(id);
            topicsName.add(name);

        }
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
        subscriptionRequest.setTopicsId(topicsId);
        subscriptionRequest.setTopicsName(topicsName);
        subscriptionRequest.setUserId(userId);
        return subscriptionRequest;

    }

}
