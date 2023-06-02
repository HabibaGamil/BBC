package com.example.post_mongo.commands;

import com.example.post_mongo.Article;
import com.example.post_mongo.MediaServer;
import com.example.post_mongo.postService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@EnableAutoConfiguration
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class addArticleCommand {

    @Autowired
    private final postService postService;

    @PostMapping("/addArticle")
    public Article execute(@RequestBody Article post) {
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

        MediaServer media = new MediaServer();
        ArrayList<String> imgIds=new ArrayList<>();
        for (int i = 0; i < post.getImages_id().size(); i++) {
            if(imagesUrl.contains(post.getImages_id().get(i))){
                for(int j=0; j<imagesUrl.size(); j++){
                    if(imagesUrl.get(j).equals(post.getImages_id().get(i))){
                        imgIds.add(imagesIds.get(j));
                        break;
                    }
                }
            }
            else {
                imagesUrl.add(post.getImages_id().get(i));
                media = new MediaServer();
                media.setMediaUrl(post.getImages_id().get(i));
                media.setType("image");
                postService.addMedia(media);
                imagesIds.add(media.getId());
                imgIds.add(media.getId());
            }
        }

        post.setImages_id(imgIds);

        ArrayList<String> vidIds =new ArrayList<>();
        for (int i = 0; i < post.getVideos_id().size(); i++) {
            media = new MediaServer();
            if(videosUrl.contains(post.getVideos_id().get(i))){
                for(int j=0; j<videosUrl.size(); j++){
                    if(videosUrl.get(j).equals(post.getVideos_id().get(i))){
                        vidIds.add(videosIds.get(j));
                        break;
                    }
                }
            }
            else {
                videosUrl.add(post.getVideos_id().get(i));
                media.setMediaUrl(post.getVideos_id().get(i));
                media.setType("video");
                postService.addMedia(media);
                videosUrl.add(media.getId());
                vidIds.add(media.getId());
            }
        }

        post.setVideos_id(vidIds);

        return postService.addArticles(post);
    }
}
