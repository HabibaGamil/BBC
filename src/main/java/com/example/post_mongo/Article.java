package com.example.post_mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Data
public class Article {
    @Id
    private String uuid;
    private String title;
    private String published_at;
    private String publisher;
    private List<String> category;
    private List<String> sub_category;
    private List<String> keywords;
    private String description;
    private String content;
    private String url;
    private List<String> image_url;
    private List<String> video_url;

    public Article(String title, String published_at, String publisher, List<String> category, List<String> sub_category, List<String> keywords, String description, String content, String url, List<String> image_url, List<String> video_url) {
        this.title = title;
        this.published_at = published_at;
        this.publisher = publisher;
        this.category = category;
        this.sub_category = sub_category;
        this.keywords = keywords;
        this.description = description;
        this.content = content;
        this.url = url;
        this.image_url = image_url;
        this.video_url = video_url;
    }

    public Article() {

    }
}
