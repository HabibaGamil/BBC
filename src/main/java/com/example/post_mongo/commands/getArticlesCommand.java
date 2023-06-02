package com.example.post_mongo.commands;

import com.example.post_mongo.Article;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.post_mongo.postService;

import java.util.List;

@EnableAutoConfiguration
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class getArticlesCommand {

    @Autowired
    private final postService postService;



    @GetMapping("/article")
    public List<Article> execute(){
        return postService.getAllArticles();
    }

}
