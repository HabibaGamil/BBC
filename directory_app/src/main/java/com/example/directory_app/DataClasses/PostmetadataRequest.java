package com.example.directory_app.DataClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostmetadataRequest {

    // whenever a new post is posted on the website

    private String postMetadataId;
    private String title;
    private String summary;
    private String imageId;
    private List<String> categoryIds;
    private List<String> subcategoryIds;
    private String topicId;
    private List<String> keywords;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date createdAt;
    private long viewCount;

}