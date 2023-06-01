package com.example.lastdemoMaven.Producer;

import com.example.lastdemoMaven.dbo.SearchRequest;
import com.example.lastdemoMaven.dbo.SearchResponse;
import com.example.lastdemoMaven.service.SearchService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
@EnableAutoConfiguration
public class RabbitMQ_Search_Producer {

    @Value("${rabbitmq.exchange.newsfeed.search}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routing_key;

    private RabbitTemplate rabbittemplate;

    @Autowired
    private SearchService search_service;

    private final int number_of_posts = 10;

    // by making this constructor, we now injected rabbit template to producer class
    public RabbitMQ_Search_Producer(RabbitTemplate rabbittemplate) {
        this.rabbittemplate = rabbittemplate;
    }

    public void send_most_viewed_message(){
       SearchRequest message_request_categories = new SearchRequest(true,0,null,number_of_posts);
       SearchResponse response_containing_categories= rabbittemplate.convertSendAndReceiveAsType(exchange, routing_key, message_request_categories,new ParameterizedTypeReference<SearchResponse>(){});
       if(response_containing_categories != null)
        search_service.update_cache(response_containing_categories);
       SearchRequest message_request_sub_categories = new SearchRequest(true,1,null,number_of_posts);
       SearchResponse response_containing_sub_categories= rabbittemplate.convertSendAndReceiveAsType(exchange, routing_key, message_request_sub_categories,new ParameterizedTypeReference<SearchResponse>(){});
        if(response_containing_sub_categories != null)
            search_service.update_cache(response_containing_sub_categories);
       SearchRequest message_request_topics = new SearchRequest(true,2,null,number_of_posts);
       SearchResponse response_containing_topics= rabbittemplate.convertSendAndReceiveAsType(exchange, routing_key, message_request_topics,new ParameterizedTypeReference<SearchResponse>(){});
       if(response_containing_topics !=null)
         search_service.update_cache(response_containing_topics);
    }
    public void send_most_recent_message(){
        SearchRequest message_request_categories = new SearchRequest(false,0,null,number_of_posts);
        SearchResponse response_containing_categories= rabbittemplate.convertSendAndReceiveAsType(exchange, routing_key, message_request_categories,new ParameterizedTypeReference<SearchResponse>(){});
        if(response_containing_categories !=null)
            search_service.update_cache(response_containing_categories);
        SearchRequest message_request_sub_categories = new SearchRequest(false,1,null,number_of_posts);
        SearchResponse response_containing_sub_categories= rabbittemplate.convertSendAndReceiveAsType(exchange, routing_key, message_request_sub_categories,new ParameterizedTypeReference<SearchResponse>(){});
       if(response_containing_sub_categories !=null)
            search_service.update_cache(response_containing_sub_categories);
        SearchRequest message_request_topics = new SearchRequest(false,2,null,number_of_posts);
        SearchResponse response_containing_topics= rabbittemplate.convertSendAndReceiveAsType(exchange, routing_key, message_request_topics,new ParameterizedTypeReference<SearchResponse>(){});
        if(response_containing_topics != null)
            search_service.update_cache(response_containing_topics);
    }
}
