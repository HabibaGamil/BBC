package com.example.lastdemoMaven.service;

import com.example.lastdemoMaven.model.hash_key;
import com.example.lastdemoMaven.model.meta_data;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;


public class post_service {

    private RedisTemplate<String, Object> redisTemplate;


    public post_service(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public void addToHashThePosts(String key, Map<String,Object> value){

        redisTemplate.opsForHash().putAll(key,value);
    }

    public void addPostToHashPosts(String key,String hashKey, Object value){
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        Map<String,Object> hash = getHash(key);
        hash.put(hashKey,value);
        hashOps.putAll(key,hash);

    }

    public Map<String, Object> getHash(String key) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        return hashOps.entries(key);
    }
}
