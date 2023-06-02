package com.example.post_mongo.commands;

import com.example.post_mongo.Article;
import com.example.post_mongo.MediaServer;
import com.example.post_mongo.postService;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class addAllArticlesCommand {

    @Autowired
    private final postService postService;

    @PostMapping("/add")
    public void execute() throws FileNotFoundException {
        File file = new File("C:\\Users\\ASUS\\Desktop\\post_mongo\\src\\main\\resources\\articleDoc.json");
        InputStream inputStream = new FileInputStream(file);
        JSONTokener tokener = new JSONTokener(inputStream);
        JSONObject jsonObject = new JSONObject(tokener);
        JSONArray jsonArray = jsonObject.getJSONArray("Article");

        // imagesUrl  idsImages videosUrl idsVideoUrl

        ArrayList<String> imagesUrl = new ArrayList<String>();
        ArrayList<String> imagesIds = new ArrayList<String>();
        ArrayList<String> videosUrl = new ArrayList<String>();
        ArrayList<String> videosIds = new ArrayList<String>();

        for(int i=0; i<postService.getAllMedia().size(); i++){
            if(postService.getAllMedia().get(i).getType().equals("image")){
                imagesUrl.add(postService.getAllMedia().get(i).getMediaUrl());
                imagesIds.add(postService.getAllMedia().get(i).getId());
            }
            else {
                videosUrl.add(postService.getAllMedia().get(i).getMediaUrl());
                videosIds.add(postService.getAllMedia().get(i).getId());
            }
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);
            System.out.println(item);
            Article a= new Article();
            MediaServer media = new MediaServer();

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

            List<String> tmp4=new ArrayList<>();
            for (int j = 0; j < item.getJSONArray("topics").length(); j++) {
                String var = (String) item.getJSONArray("topics").get(j);
                tmp4.add(var);
            }
            a.setTopics(tmp4);

            a.setDescription(item.getString("description"));
            a.setContent(item.getString("content"));
            a.setPublisher(item.getString("publisher"));

            ArrayList<String> imgIds=new ArrayList<>();
            for (int j = 0; j < item.getJSONArray("image_url").length(); j++) {
                String var = (String) item.getJSONArray("image_url").get(j);
                if(imagesUrl.contains(var)){
                    for(int x=0; x<imagesUrl.size(); x++){
                        if(imagesUrl.get(x).equals(var)){
                            imgIds.add(imagesIds.get(x));
                            break;
                        }
                    }
                }
                else {
                    imagesUrl.add(var);
                    media = new MediaServer();
                    media.setMediaUrl(var);
                    media.setType("image");
                    postService.addMedia(media);
                    imagesIds.add(media.getId());
                    imgIds.add(media.getId());
                }
            }

            a.setImages_id(imgIds);

            ArrayList<String> vidIds =new ArrayList<>();
            for (int j = 0; j < item.getJSONArray("video_url").length(); j++) {
                String var = (String) item.getJSONArray("video_url").get(j);
                media = new MediaServer();
                if(videosUrl.contains(var)){
                    for(int x=0; x<videosUrl.size(); x++){
                        if(videosUrl.get(x).equals(var)){
                            vidIds.add(videosIds.get(x));
                            break;
                        }
                    }
                }
                else {
                    videosUrl.add(var);
                    media.setMediaUrl(var);
                    media.setType("video");
                    postService.addMedia(media);
                    videosUrl.add(media.getId());
                    vidIds.add(media.getId());
                }
            }

            a.setVideos_id(vidIds);
            postService.addArticles(a);


        }
    }

}
