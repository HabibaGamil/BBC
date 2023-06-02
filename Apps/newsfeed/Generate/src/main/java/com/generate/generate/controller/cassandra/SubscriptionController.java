package com.generate.generate.controller.cassandra;



import com.generate.generate.dbo.cassandra.SubscriptionRequest;
import com.generate.generate.dbo.cassandra.ViewRequest;
import com.generate.generate.service.cassandra.SubscriptionService;
import com.generate.generate.test.SubscriptionProducerTest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {

    private final SubscriptionProducerTest subscriptionProducerTest;

    @GetMapping("/test")
    public SubscriptionRequest testSubscription(){
        SubscriptionRequest subscriptionRequest = subscriptionProducerTest.publishSubscription();
        return subscriptionRequest;
    }
}
