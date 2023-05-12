package com.example.lastdemoMaven.controller;

import com.example.lastdemoMaven.config.RedisConfig;
import com.example.lastdemoMaven.model.hash_key;
import com.example.lastdemoMaven.service.post_service;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;


public class Post_Controller {
    private final post_service postService;
    private final String key= "Posts_Hash";

    public Post_Controller() {
        RedisConfig rc = new RedisConfig();
        RedisTemplate<String, Object> rt =  rc.template();
        postService = new post_service(rt);
    }
    public void updatePostHash(Map<String,Object> map_comes_from_event){
        postService.addToHashThePosts(key,map_comes_from_event);
    }
    public Map<String,Object> getPostHash(){
        return postService.getHash(key);
    }

}
