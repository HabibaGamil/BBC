package com.generate.generate.Producer;


import com.generate.generate.dbo.redis.SearchRequest;
import com.generate.generate.dbo.redis.SearchResponse;
import com.generate.generate.service.redis.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.integration.support.locks.ExpirableLockRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.util.concurrent.locks.Lock;

@Service
@RequiredArgsConstructor
@EnableAutoConfiguration
@Slf4j
public class SearchProducer {

    @Value("${rabbitmq.exchange.newsfeed.search}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routing_key;

    private final RabbitTemplate rabbittemplate;

    private final SearchService search_service;

    private final int number_of_posts = 10;


    private static final int timeToUpdateMostViewed = 27133;

    private static final int timeToUpdateMostRecent = 15000;


    private final ExpirableLockRegistry lockRegistry;



    private static final String lockKey = "updateCache";

    // by making this constructor, we now injected rabbit template to producer class

    @Scheduled(fixedRate = timeToUpdateMostViewed)
    public void scheduleMostView(){
        Lock lock = lockRegistry.obtain(lockKey);
        try {
            boolean success = lock.tryLock();
            if(success) {
                send_most_viewed_message();
            }
            else{
                log.info("lock not acquired tried ");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }


    public void send_most_viewed_message(){
       SearchRequest message_request_categories = new SearchRequest(true,0,null,number_of_posts);
       SearchResponse response_containing_categories= rabbittemplate.convertSendAndReceiveAsType(exchange, routing_key, message_request_categories,new ParameterizedTypeReference<SearchResponse>(){});
      if (response_containing_categories != null)
        {
            search_service.update_cache(response_containing_categories);
            log.info("category " + response_containing_categories);
        }

       SearchRequest message_request_sub_categories = new SearchRequest(true,1,null,number_of_posts);
       SearchResponse response_containing_sub_categories= rabbittemplate.convertSendAndReceiveAsType(exchange, routing_key, message_request_sub_categories,new ParameterizedTypeReference<SearchResponse>(){});
       if(response_containing_sub_categories != null){
           search_service.update_cache(response_containing_sub_categories);
           log.info("subCategory "+response_containing_sub_categories);
       }

       SearchRequest message_request_topics = new SearchRequest(true,2,null,number_of_posts);
       SearchResponse response_containing_topics= rabbittemplate.convertSendAndReceiveAsType(exchange, routing_key, message_request_topics,new ParameterizedTypeReference<SearchResponse>(){});
      if(response_containing_topics != null) {
          search_service.update_cache(response_containing_topics);
          log.info("topics " + response_containing_topics);
      }
    }

    @Scheduled(fixedRate = timeToUpdateMostRecent)
    public void scheduleMostRecent(){
        Lock lock = lockRegistry.obtain(lockKey);
        try {
            boolean success = lock.tryLock();
            if(success) {
                send_most_recent_message();
            }
            else{
                log.info("lock not acquired tried ");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

    public void send_most_recent_message(){
        SearchRequest message_request_categories = new SearchRequest(false,0,null,number_of_posts);
        SearchResponse response_containing_categories= rabbittemplate.convertSendAndReceiveAsType(exchange, routing_key, message_request_categories,new ParameterizedTypeReference<SearchResponse>(){});
        if(response_containing_categories != null) {
            search_service.update_cache(response_containing_categories);
            log.info("category " + response_containing_categories);
        }


        SearchRequest message_request_sub_categories = new SearchRequest(false,1,null,number_of_posts);
        SearchResponse response_containing_sub_categories= rabbittemplate.convertSendAndReceiveAsType(exchange, routing_key, message_request_sub_categories,new ParameterizedTypeReference<SearchResponse>(){});
        if(response_containing_sub_categories != null) {
            search_service.update_cache(response_containing_sub_categories);
            log.info("subCategory " + response_containing_sub_categories);
        }


        SearchRequest message_request_topics = new SearchRequest(false,2,null,number_of_posts);
        SearchResponse response_containing_topics= rabbittemplate.convertSendAndReceiveAsType(exchange, routing_key, message_request_topics,new ParameterizedTypeReference<SearchResponse>(){});
        if(response_containing_topics != null) {
            search_service.update_cache(response_containing_topics);
            log.info("topics " + response_containing_topics);
        }
    }
}
