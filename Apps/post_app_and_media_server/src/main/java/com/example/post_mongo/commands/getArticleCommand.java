package com.example.post_mongo.commands;

import com.example.post_mongo.Article;
import com.example.post_mongo.postService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@EnableAutoConfiguration
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class getArticleCommand {

    @Autowired
    private final postService postService;

    @GetMapping("/article/{id}")
    public Optional<Article> execute(@PathVariable String id){
       return postService.getArticle(id);
    }
}
