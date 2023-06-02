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

import java.util.List;
import java.util.Optional;

@EnableAutoConfiguration
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class getPostMetaDataCommand {

    @Autowired
    private final com.example.post_mongo.postService postService;

    @GetMapping("/metaData/{id1}")
    public String execute(@PathVariable String id1){
        System.out.println("METADATA");
        Article a = postService.getArticle(id1).get();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", a.getUuid());
        jsonObject.put("title", a.getTitle());
        jsonObject.put("published_at", a.getPublished_at());
        jsonObject.put("description", a.getDescription());
        jsonObject.put("image", a.getImages_id().get(0));
        jsonObject.put("sub_category", a.getSub_category());
        return jsonObject+"";
    }

}
