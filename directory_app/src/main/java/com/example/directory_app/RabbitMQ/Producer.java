package com.example.directory_app.RabbitMQ;

import com.example.directory_app.DataClasses.SearchRequest;
import com.example.directory_app.DataClasses.SearchResponse;
import com.example.directory_app.DataClasses.UpdateViewCountEvent;
import com.example.directory_app.DataClasses.ViewsResponse;
import com.example.directory_app.entities.PostMetadataEntity;
import com.example.directory_app.entities.Views;
import com.example.directory_app.services.PostMetadataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableAutoConfiguration
public class Producer {

    RabbitTemplate rabbitTemplate;
    @Autowired RabbitMQConfig rabbitMQConfig;

    @Autowired
    private PostMetadataService postsMetadataService;

    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Value("newsfeed_dir_exchange")
    private String newsfeedExchange;

    @Value("view_count_exchange")
    private String viewCountExchange;


    public SearchResponse sendMessageToSendPostsToNewsfeed(SearchRequest req){

        LOGGER.info(String.format("api gateway request sent to RabbitMQ => %s", req.toString()));
        LOGGER.info(String.format("api gateway request sent to RabbitMQ => %s", rabbitMQConfig.getRoutingKeyMap()));

        String routingKey = "newsfeed_dir_routing_key";

        LOGGER.info(String.format("Routing key=> %s", routingKey));

        SearchResponse response = rabbitTemplate
                .convertSendAndReceiveAsType(newsfeedExchange, routingKey, req, new ParameterizedTypeReference<>() {});

        LOGGER.info(String.format("Response", response));

        return response;

    }

    public void sendMessageToUpdateViewCount(UpdateViewCountEvent req){

        LOGGER.info(String.format("api gateway request sent to RabbitMQ => %s", req.toString()));
        LOGGER.info(String.format("api gateway request sent to RabbitMQ => %s", rabbitMQConfig.getRoutingKeyMap()));

        String routingKey = "view_count_routing_key";

        LOGGER.info(String.format("Routing key=> %s", routingKey));

        ViewsResponse response = rabbitTemplate
                .convertSendAndReceiveAsType(viewCountExchange, routingKey, req, new ParameterizedTypeReference<>() {});


        List<Views> views_records = response.getViews();
        for(Views view: views_records){

            PostMetadataEntity postMetadataEntity = postsMetadataService.findById(view.getPostID());

            long postCurrentViewCount = postMetadataEntity.getViewCount();
            postMetadataEntity.setViewCount( postCurrentViewCount + view.getView_count());

            postsMetadataService.save(postMetadataEntity);

        }



        LOGGER.info(String.format("Response", response));

    }
}