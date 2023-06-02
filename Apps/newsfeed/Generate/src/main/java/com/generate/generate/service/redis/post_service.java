package com.generate.generate.service.redis;

import com.generate.generate.dbo.redis.PostEvent;
import com.generate.generate.repository.redis.posts_repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class post_service {



    private final posts_repository postsRepository;
    private final int maxNumberOfPosts = 5;




    public void addToHashPosts(PostEvent postEvent){
        Object post_meta_data = postEvent.getPost_meta_data();
        Map<String,Object> hash = new LinkedHashMap<>(postsRepository.get_posts_hash());
        List<String> keysAsList = new ArrayList<>(hash.keySet());
        if(keysAsList.size()<maxNumberOfPosts) {
                hash.put(String.valueOf(keysAsList.size()),post_meta_data);
        }
        else{
            for (int i = 0; i<maxNumberOfPosts - 1;i++){

                hash.put(String.valueOf(i),hash.remove(String.valueOf(i + 1)));
            }
            hash.put(String.valueOf(maxNumberOfPosts - 1),post_meta_data);
        }
        postsRepository.addAll(hash);
    }




    public Map<String,Object> getAllPosts(){
        return postsRepository.get_posts_hash();
    }

//    public Map<String, Object> getHash(String key) {
//        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
//        return hashOps.entries(key);
//    }
//
//    public void deleteHash(String key) {
//        redisTemplate.delete(key);
//    }
}