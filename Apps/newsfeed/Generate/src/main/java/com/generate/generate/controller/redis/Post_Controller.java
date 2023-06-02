package com.generate.generate.controller.redis;


import com.generate.generate.config.RedisConfig;
import com.generate.generate.dbo.redis.PostEvent;
import com.generate.generate.test.PostProducerTest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import com.generate.generate.service.redis.post_service;
import org.springframework.web.bind.annotation.*;


import java.util.Map;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Slf4j
public class Post_Controller {

    private final PostProducerTest postProducerTest;
    private final post_service postService;



    @GetMapping("/test")
    public PostEvent testPost(){
        PostEvent postEvent = postProducerTest.publishPost();
        return postEvent;
    }


    @PostMapping("/add_post")
    public String addPost(@RequestBody PostEvent postEvent){
        postService.addToHashPosts(postEvent);
        return "added";
    }

    @GetMapping("/get_post")
    public Map<String,Object> getPosts(){
        return postService.getAllPosts();
    }


}
