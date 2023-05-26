package com.example.lastdemoMaven.service;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;


public class post_service {

    private final RedisTemplate<String, Object> redisTemplate;

    public post_service(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }


    public void addToHashPosts(String key, Object post_meta_data){
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        Map<String,Object> hash = new LinkedHashMap<>(getHash(key));
        List<String> keysAsList = new ArrayList<>(hash.keySet());
        Collections.sort(keysAsList);
        String lastKey;
        if(keysAsList.size()<5) {
            if(keysAsList.size() == 0)
                hash.put(0+"",post_meta_data);
            else{
                lastKey = keysAsList.get(keysAsList.size() - 1);
                hash.put((Integer.parseInt(lastKey)+1)+"",post_meta_data);
            }
        }
        else{
            for (int i = 0; i<4;i++){
                System.out.println();
                hash.put(i+"",hash.remove((i+1)+""));
            }
            hash.put("4",post_meta_data);
        }
        hashOps.putAll(key,hash);
    }

    public Map<String, Object> getHash(String key) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        return hashOps.entries(key);
    }

    public void deleteHash(String key) {
        redisTemplate.delete(key);
    }
}
