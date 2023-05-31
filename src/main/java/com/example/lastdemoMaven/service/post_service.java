package com.example.lastdemoMaven.service;

import com.example.lastdemoMaven.dbo.PostEvent;
import com.example.lastdemoMaven.model.PostMetadataEntity;
import com.example.lastdemoMaven.repository.posts_repository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class post_service {


    private  posts_repository postsRepository;
    private final int maxNumberOfPosts = 5;



    public void addToHashPosts(PostEvent ps){
        PostMetadataEntity post_meta_data =  ps.getPost_meta_data();
        Map<String,Object> hash = new LinkedHashMap<>(postsRepository.get_posts_hash());
        List<String> keysAsList = new ArrayList<>(hash.keySet());
        Collections.sort(keysAsList);
        String lastKey;
        if(keysAsList.size()<maxNumberOfPosts) {
            if(keysAsList.size() == 0)
                hash.put(0+"",post_meta_data);
            else{
                lastKey = keysAsList.get(keysAsList.size() - 1);
                hash.put(String.valueOf(Integer.parseInt(lastKey)+1),post_meta_data);
            }
        }
        else{
            for (int i = 0; i<4;i++){
                System.out.println();
                hash.put(String.valueOf(i),hash.remove(String.valueOf(i+1)));
            }
            hash.put(String.valueOf(maxNumberOfPosts - 1),post_meta_data);
        }
        postsRepository.addAll(hash);
    }
}
