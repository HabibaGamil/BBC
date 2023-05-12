package com.example.post_mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;


import java.util.ArrayList;
import java.util.List;

@Data
public class Article {
    @Id
    private String uuid;
    private String title;
    private String published_at;
    private String publisher;
    private List<String>  category;
    private List<String> sub_category;
    private List<String> keywords;
    private String description;
    private String content;
    private String url;
    private ArrayList<String> images_id;
    private ArrayList<String> videos_id;



    public Article(String title, String published_at, String publisher, List<String> category, List<String> sub_category, List<String> keywords, String description, String content, String url, ArrayList<String> images_id, ArrayList<String> videos_id) {
        this.title = title;
        this.published_at = published_at;
        this.publisher = publisher;
        this.category = category;
        this.sub_category = sub_category;
        this.keywords = keywords;
        this.description = description;
        this.content = content;
        this.url = url;
        this.images_id = images_id;
        this.videos_id= videos_id;
    }

    public Article() {

    }


    public void setCategory(List<String> category) {
        this.category = category;
    }

    public void setImages_id(ArrayList<String> images_id) {
        this.images_id = images_id;
    }

    public void setVideos_id(ArrayList<String> videos_id) {
        this.videos_id = videos_id;
    }

}
