package com.example.springbootelasticsearch.RabbitMQ;

import com.example.springbootelasticsearch.service.PostsMetadataSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
@EnableAutoConfiguration
public class Producer {

    RabbitTemplate rabbitTemplate;
    @Autowired
    RabbitMQConfig rabbitMQConfig;

    @Autowired
    private PostsMetadataSearchService postsMetadataService;

    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Value("newsfeed_dir_exchange")
    private String newsfeedExchange;

    @Value("view_count_exchange")
    private String viewCountExchange;


//    public SearchResponse sendMessageToSendPostsToNewsfeed(SearchRequest req){
//
//        LOGGER.info(String.format("api gateway request sent to RabbitMQ => %s", req.toString()));
//        LOGGER.info(String.format("api gateway request sent to RabbitMQ => %s", rabbitMQConfig.getRoutingKeyMap()));
//
//        String routingKey = "newsfeed_dir_routing_key";
//
//        LOGGER.info(String.format("Routing key=> %s", routingKey));
//
//        SearchResponse response = rabbitTemplate
//                .convertSendAndReceiveAsType(newsfeedExchange, routingKey, req, new ParameterizedTypeReference<>() {});
//
//        LOGGER.info(String.format("Response", response));
//
//        return response;
//
//    }

}