package com.example.lastdemoMaven.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;

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
public class meta_data  implements Serializable {

    String article_id;
    String title;
    String summary;
    String image_id;
    String [] ids;
    String [] keywords;
    String date;
    String view_count;
    String name;

    @Override
    public String toString() {
        return "meta_data{" +
                "article_id='" + article_id + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", image_id='" + image_id + '\'' +
                ", ids=" + Arrays.toString(ids) +
                ", keywords=" + Arrays.toString(keywords) +
                ", date='" + date + '\'' +
                ", view_count='" + view_count + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
