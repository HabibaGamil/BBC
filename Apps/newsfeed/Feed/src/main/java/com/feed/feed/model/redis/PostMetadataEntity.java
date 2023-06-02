package com.feed.feed.model.redis;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*
  JSONObject that will come from response
      "article_id":"",
    "title":"",
    "summary":"",
    "image_id":"",
    "category_ids": [], keywords : [],
    "date":"",
    "view_count": ""*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostMetadataEntity implements Serializable {

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
    private int viewCount;

//    @Override
//    public String toString() {
//        return "PostMetadataEntity{" +
//                "article_id='" + article_id + '\'' +
//                ", title='" + title + '\'' +
//                ", summary='" + summary + '\'' +
//                ", image_id='" + image_id + '\'' +
//                ", ids=" + Arrays.toString(ids) +
//                ", keywords=" + Arrays.toString(keywords) +
//                ", date='" + date + '\'' +
//                ", view_count='" + view_count + '\'' +
//                ", name='" + name + '\'' +
//                '}';
//    }
}
