package com.example.lastdemoMaven.controller;

import com.example.lastdemoMaven.config.RedisConfig;
import com.example.lastdemoMaven.repository.posts_repository;
import com.example.lastdemoMaven.service.post_service;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;


public class Post_Controller {
    private final post_service postService;
    private final posts_repository pr;
    private final String key= "Post_Hash";

    public Post_Controller() {
        RedisConfig rc = new RedisConfig();
        RedisTemplate<String, Object> rt =  rc.template();
        postService = new post_service(rt);
        pr = new posts_repository(rt);
    }


    public void addPostToTheHash(Object meta_data){
        postService.addToHashPosts(key,meta_data);
    }
    public Map<String,Object> getPostHash(){
        return postService.getHash(key);
    }

    public void deleteTheHash(){
        postService.deleteHash(key);
    }


}
