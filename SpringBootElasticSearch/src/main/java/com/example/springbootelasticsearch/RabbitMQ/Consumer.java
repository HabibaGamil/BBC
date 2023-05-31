package com.example.springbootelasticsearch.RabbitMQ;

import com.example.springbootelasticsearch.DataClasses.ViewsBroadcastRequest;
import com.example.springbootelasticsearch.config.Config;
import com.example.springbootelasticsearch.config.Properties;
import com.example.springbootelasticsearch.entity.PostMetadataEntity;
import com.example.springbootelasticsearch.entity.Views;
import com.example.springbootelasticsearch.service.PostsMetadataSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Configuration
@EnableAutoConfiguration
//@EnableConfigurationProperties(Config.class)
public class Consumer {
    @Autowired
    private PostsMetadataSearchService postsMetadataService;
    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;
    @Autowired
    Config config;
    boolean disabled;
    Properties properties;
    private Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    void setProperties () throws ClassNotFoundException {
        if(this.properties==null){
            properties=new Properties(config.getMsg(),config.getCmdMap(),config.getModifiableClasses());
        }
    }

    private static final Map<Integer, String> type_mappings = new HashMap<Integer, String>() {{
        put(0, "categoryIds");
        put(1,"subcategoryIds");
        put(2,"topicId");
    }};



    @RabbitListener(queues = "view_count_search_queue")
    public void consumeMQServerMessageToUpdatePostViewCount(ViewsBroadcastRequest request) {
        // loop over view list
        // get the post metadata id
        // update the view count of the post metadata

        List<Views> views_records = request.getViews();
        for(Views view: views_records){

            PostMetadataEntity postMetadataEntity = postsMetadataService.findById(view.getPostID());

            if(postMetadataEntity!=null) {

                long postCurrentViewCount = postMetadataEntity.getViewCount();
                postMetadataEntity.setViewCount(postCurrentViewCount + view.getView_count());

                postsMetadataService.index(postMetadataEntity);
            }
        }

        LOGGER.info("post metadatas' view count in SEARCH is updated!");
    }
}