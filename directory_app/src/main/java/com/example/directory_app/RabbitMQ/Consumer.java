package com.example.directory_app.RabbitMQ;

import com.example.directory_app.DataClasses.SearchRequest;
import com.example.directory_app.DataClasses.SearchResponse;
import com.example.directory_app.DataClasses.ViewsBroadcastRequest;
import com.example.directory_app.config.Config;
import com.example.directory_app.config.Properties;
import com.example.directory_app.entities.PostMetadataEntity;
import com.example.directory_app.entities.Views;
import com.example.directory_app.search.SearchRequestDTO;
import com.example.directory_app.services.PostMetadataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Configuration
@EnableAutoConfiguration
//@EnableConfigurationProperties(Config.class)
public class Consumer {
    @Autowired
    private PostMetadataService postsMetadataService;
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


    @RabbitListener(queues = "newsfeed_dir_queue")
    public  SearchResponse consumeMQServerMessage(SearchRequest request){
        List<List<PostMetadataEntity>> postMetadataEntities = new ArrayList<>();

        List<String> categoryIds= request.getIds();

        try {
            for(int i=0; i<categoryIds.size();i++) {
                SearchRequestDTO dto = SearchRequestDTO.builder()
                        .searchTerm((request.getIds() == null) ? "" : request.getIds().get(i))
                        .fields(List.of(type_mappings.get(request.getType())))
                        .sortBy(request.isMost_viewed() ? "viewCount" : "date")
                        .build();
                dto.setPage(0);
                dto.setSize(request.getNumber_of_posts());


                setProperties();

                Class c = properties.getCmdMap().get("searchAction");

                Object obj = c.getConstructor().newInstance();
                autowireCapableBeanFactory.autowireBean(obj);
                Method execute = c.getDeclaredMethod("execute", SearchRequestDTO.class);
                List<PostMetadataEntity> postMetadataEntities_sub = (List<PostMetadataEntity>) execute.invoke(obj, dto);
                postMetadataEntities.add(postMetadataEntities_sub);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        SearchResponse searchResponse = SearchResponse.builder()
                .most_viewed(request.isMost_viewed())
                .ids(categoryIds)
                .type(request.getType())
                .number_of_posts(postMetadataEntities.size())
                .metadata(postMetadataEntities)
                .build();

        LOGGER.info(String.format("Request Received from MQ server => %s", request.toString()));
        return searchResponse;
    }

    @RabbitListener(queues = "view_count_dir_queue")
    public void consumeMQServerMessageToUpdatePostViewCount(ViewsBroadcastRequest request) {
        // loop over view list
        // get the post metadata id
        // update the view count of the post metadata

        List<Views> views_records = request.getViews();
        for(Views view: views_records){

            PostMetadataEntity postMetadataEntity = postsMetadataService.findById(view.getPostID());

            if(postMetadataEntity != null) {
                long postCurrentViewCount = postMetadataEntity.getViewCount();
                postMetadataEntity.setViewCount(postCurrentViewCount + view.getView_count());

                postsMetadataService.index(postMetadataEntity);
            }
        }

        LOGGER.info("post metadatas' view count in DIRECTORY is updated!");
    }
}