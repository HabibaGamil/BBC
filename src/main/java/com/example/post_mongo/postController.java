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
import java.util.ArrayList;
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

    @PostMapping("/addAll")
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
            a.setPublished_at(item.getString("published_at"));

            List<String> tmp=new ArrayList<>();
            for (int j = 0; j < item.getJSONArray("category").length(); j++) {
                String var = (String) item.getJSONArray("category").get(j);
                tmp.add(var);
            }
            a.setCategory(tmp);


            List<String> tmp2=new ArrayList<>();
            for (int j = 0; j < item.getJSONArray("sub_category").length(); j++) {
                String var = (String) item.getJSONArray("sub_category").get(j);
                tmp2.add(var);
            }
            a.setSub_category(tmp2);

            List<String> tmp3=new ArrayList<>();
            for (int j = 0; j < item.getJSONArray("keywords").length(); j++) {
                String var = (String) item.getJSONArray("keywords").get(j);
                tmp3.add(var);
            }
            a.setKeywords(tmp3);


            a.setDescription(item.getString("description"));
            a.setContent(item.getString("content"));
            a.setPublisher(item.getString("publisher"));

            List<String> tmp4=new ArrayList<>();
            for (int j = 0; j < item.getJSONArray("image_url").length(); j++) {
                String var = (String) item.getJSONArray("image_url").get(j);
                tmp4.add(var);
            }
            a.setImage_url(tmp4);

            List<String> tmp5=new ArrayList<>();
            for (int j = 0; j < item.getJSONArray("video_url").length(); j++) {
                String var = (String) item.getJSONArray("video_url").get(j);
                tmp5.add(var);
            }
            a.setVideo_url(tmp5);


            postService.addArticles(a);

        }
    }


}
