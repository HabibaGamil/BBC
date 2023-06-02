package com.feed.feed.commands;

import com.feed.feed.dbo.request.CategoryRequest;
import com.feed.feed.dbo.request.Request;
import com.feed.feed.dbo.request.SubCategoryRequest;
import com.feed.feed.dbo.response.CategoryResponse;
import com.feed.feed.dbo.response.Response;
import com.feed.feed.dbo.response.SubCategoryResponse;
import com.feed.feed.model.redis.category_data;
import com.feed.feed.model.redis.sub_category_data;
import com.feed.feed.repository.redis.sub_categories_repository;
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
public class SubCategoryCommand implements Command{

    private final sub_categories_repository SCR;
    @Override
    @Cacheable(value = "newsfeed-PostsData", key = "#request.cacheParam()")
    public Response execute(Request request) {
        log.info("SubCategory Command called "+request.cacheParam());
        SubCategoryRequest requestCast = (SubCategoryRequest) request;
        SubCategoryResponse response = new SubCategoryResponse();
        response.setSubCategoryId(requestCast.getSubCategoryId());
        Optional<sub_category_data> posts =  SCR.findById(requestCast.getSubCategoryId());
        if(posts.isPresent()){
            response.setPosts(posts.get());
        }
        return response;
    }
}
