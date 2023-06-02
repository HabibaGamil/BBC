package com.generate.generate.consumer.cassandra;



import com.generate.generate.dbo.cassandra.SubscriptionRequest;
import com.generate.generate.service.cassandra.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service

@RequiredArgsConstructor
@Slf4j
public class SubscriptionConsumer {

    private final SubscriptionService subscriptionService;


    @RabbitListener(queues = "${queue.newsfeed.user.userSubscribe}")
    public void subscribe(@RequestBody SubscriptionRequest subscriptionRequest){
        log.info("subscribe consumer");
        subscriptionService.subscribe(subscriptionRequest);

    }
}
