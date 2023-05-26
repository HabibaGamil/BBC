package com.example.lastdemoMaven.repository;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

@AllArgsConstructor
public class posts_repository {
    private final RedisTemplate<String, Object> redisTemplate;

    public Map<String, Object> get_posts_hash(String key) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        return hashOps.entries(key);
    }

}
