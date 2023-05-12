//package com.example.lastdemoMaven.service;
//
//import com.example.lastdemoMaven.model.post_data;
//import com.example.lastdemoMaven.model.redis_hash;
//import com.example.lastdemoMaven.repository.post_repository;
//import lombok.Getter;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//
//@Service
//public class PostService {
//
//    private final post_repository pr;
//    private static redis_hash redisHash;
//
//    public PostService(post_repository pr) {
//        this.pr = pr;
//        redisHash = new redis_hash("key");
//        pr.save(redisHash);
//    }
//
//    public void insertIntoCache(post_data newPost){
//        HashMap<String,post_data> hash = redisHash.getHashMap();
//        hash.put(newPost.getKey(),newPost);
//        pr.save(redisHash);
//    }
//    public post_data getSpecificPostById(String key){
//        HashMap<String,post_data> hash = redisHash.getHashMap();
//        return hash.get(key);
//    }
//    public void deleteSpecificPostById(String key){
//        HashMap<String,post_data> hash = redisHash.getHashMap();
//        hash.remove(key);
//        redisHash.setHashMap(hash);
//        pr.save(redisHash);
//    }
//}
