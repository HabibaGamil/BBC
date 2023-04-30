package com.example.post_mongo;

import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class postController {

    @Autowired
    private final postService postService;

    @GetMapping("/article")
    public List<Article> fetchAllArticles(){
        return postService.getAllArticles();
    }

    @PostMapping("/add")
    public Article addArticles(@RequestBody Article post){
        return postService.addArticles(post);
    }

    public void addALL() throws FileNotFoundException {
        File file = new File("E:\\SEM 10\\scalable\\post_mongo\\src\\main\\resources\\articleDoc.json");
        InputStream inputStream = new FileInputStream(file);
        JSONTokener tokener = new JSONTokener(inputStream);
        JSONObject jsonObject = new JSONObject(tokener);
        JSONArray jsonArray = jsonObject.getJSONArray("Article");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            System.out.println(item);
            Article a= new Article();
            a.setTitle(item.getString("title"));
            a.setPublished_at(item.getString("Published_at"));
            a.setPublisher(item.getString("Publisher"));

          //  a.setCategory(item.get("Category"));




        }
    }


}
