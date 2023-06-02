package com.feed.feed.commands;


import com.feed.feed.dbo.request.CategoryRequest;
import com.feed.feed.dbo.request.Request;
import com.feed.feed.dbo.response.CategoryResponse;
import com.feed.feed.model.redis.category_data;
import com.feed.feed.repository.redis.categories_repository;
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
public class CategoryCommand implements Command{
    private final categories_repository CR;

    @Override
    @Cacheable(value = "newsfeed-PostsData", key = "#request.cacheParam()")
    public CategoryResponse execute(Request request) {
        log.info("CategoryCommand called "+request.cacheParam());
        CategoryRequest requestCast = (CategoryRequest) request;
        CategoryResponse response = new CategoryResponse();
        response.setCategoryId(requestCast.getCategoryId());
        Optional<category_data> posts =  CR.findById(requestCast.getCategoryId());
        if(posts.isPresent()){
            response.setPosts(posts.get());
        }
        return response;
    }
}
