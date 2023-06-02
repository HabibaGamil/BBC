package com.example.post_mongo.commands;

import com.example.post_mongo.Article;
import com.example.post_mongo.postService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@EnableAutoConfiguration
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class getCategoryCommand {

    @Autowired
    private final com.example.post_mongo.postService postService;

    @GetMapping("/category/{postId}/{userId}")
    public String execute(@PathVariable String postId,@PathVariable String userId){
        Article a = postService.getArticle(postId).get();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("category", a.getCategory());

        return jsonObject+"";
    }

}
