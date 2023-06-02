package com.feed.feed.repository.redis;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@AllArgsConstructor
@Repository
public class posts_repository {
    private final RedisTemplate<String, Object> redisTemplate;
    private final String key= "Posts_Hash";
    public Map<String, Object> get_posts_hash() {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        return hashOps.entries(key);
    }

    public void deleteHash() {
        redisTemplate.delete(key);
    }

    public void addAll(Map<String,Object> hash){
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        hashOps.putAll(key,hash);
    }

}
