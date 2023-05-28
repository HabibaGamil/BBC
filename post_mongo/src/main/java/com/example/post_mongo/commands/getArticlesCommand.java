package com.example.post_mongo.commands;

import com.example.post_mongo.Article;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.post_mongo.postService;

import java.util.List;


@AllArgsConstructor
public class getArticlesCommand {

    @Autowired
    private final postService postService;


    public List<Article> execute(){
        return postService.getAllArticles();
    }

}
