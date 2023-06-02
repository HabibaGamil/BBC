package com.feed.feed.commands;

import com.feed.feed.dbo.request.CategoryRequest;
import com.feed.feed.dbo.request.Request;
import com.feed.feed.dbo.request.TopicRequest;
import com.feed.feed.dbo.response.CategoryResponse;
import com.feed.feed.dbo.response.TopicResponse;
import com.feed.feed.model.redis.category_data;
import com.feed.feed.model.redis.topic_data;
import com.feed.feed.repository.redis.topics_repository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class TopicCommand implements Command{

    private final topics_repository TD;
    @Override
    @Cacheable(value = "newsfeed-PostsData", key = "#request.cacheParam()")
    public TopicResponse execute(Request request) {
        log.info("TopicCommand called "+request.cacheParam());
        TopicRequest requestCast = (TopicRequest) request;
        TopicResponse response = new TopicResponse();
        response.setTopicId(requestCast.getTopicId());
        Optional<topic_data> posts =  TD.findById(requestCast.getTopicId());
        if(posts.isPresent()){
            response.setPosts(posts.get());
        }
        return response;
    }
}
