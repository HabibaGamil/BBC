package com.example.post_mongo.commands;

import com.example.post_mongo.Article;
import com.example.post_mongo.postService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@AllArgsConstructor
public class getArticleCommand {

    @Autowired
    private final postService postService;


    public Optional<Article> execute(@PathVariable String id){
       return postService.getArticle(id);
    }
}
