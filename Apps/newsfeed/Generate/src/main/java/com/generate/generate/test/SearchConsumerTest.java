package com.generate.generate.test;


import com.generate.generate.dbo.cassandra.SubscriptionRequest;
import com.generate.generate.dbo.redis.SearchRequest;
import com.generate.generate.dbo.redis.SearchResponse;
import com.generate.generate.model.redis.PostMetadataEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service

@RequiredArgsConstructor
@Slf4j
public class SearchConsumerTest {



    @RabbitListener(queues = "${rabbitmq.queue.newsfeed.search}")
    public SearchResponse search(SearchRequest searchRequest){
        log.info(searchRequest.toString());

        SearchResponse searchResponse = new SearchResponse();
        int len = (int)(1 + Math.random()*5);
        searchResponse.setIds(idsList(len));
        searchResponse.setMetadata(getMetadata(len));
        searchResponse.setType(searchRequest.getType());
        searchResponse.setMost_viewed(searchRequest.isMost_viewed());
        searchResponse.setNumber_of_posts(searchRequest.getNumber_of_posts());
        return searchResponse;

    }

    private List<String> idsList(int len){
        List<String> ids = new ArrayList<>();
        for(int i = 0 ; i<len;i++){
            int x = (int)(1 + Math.random()*5);
            ids.add(String.valueOf(x));
        }
        return  ids;
    }


    private List<List<PostMetadataEntity>> getMetadata(int len){

        List<List<PostMetadataEntity>> metadata = new ArrayList<>();
        for(int i = 0 ; i<len;i++){
            int x = (int)(1 + Math.random()*5);
            metadata.add(getListMeta(x));
        }
        return metadata;

    }
    private List<PostMetadataEntity> getListMeta(int len){
        List<PostMetadataEntity> meta = new ArrayList<>();

        for(int i = 0 ; i<len;i++){

            meta.add(postMeta());
        }

        return meta;

    }

    private PostMetadataEntity postMeta(){
        PostMetadataEntity postMetadataEntity = new PostMetadataEntity();
        int ran = (int)(Math.random()*100);
        postMetadataEntity.setPostMetadataId(String.valueOf(ran));
        char c = (char)('a'+ran%26);
        String title = String.valueOf(c)+c+c;
        String summary = String.valueOf(c)+c+c+c;
        postMetadataEntity.setTitle(title);
        postMetadataEntity.setSummary(summary);
        return postMetadataEntity;

    }



}
