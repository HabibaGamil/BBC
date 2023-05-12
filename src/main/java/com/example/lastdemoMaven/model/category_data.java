package com.example.lastdemoMaven.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Arrays;

@RedisHash("Categories_data")
@AllArgsConstructor
@Getter
@Setter
public class category_data implements Serializable {


    @Id
    private String category_id;

    private String category_name;

    // old version JSONObject[] most_viewed;
    private meta_data [] most_viewed;

    // old version JSONObject[] most_recent;
    private meta_data [] most_recent;

    @Override
    public String toString() {
        return "category_data{" +
                "category_id='" + category_id + '\'' +
                ", category_name='" + category_name + '\'' +
                ", most_viewed=" + Arrays.toString(most_viewed) +
                ", most_recent=" + Arrays.toString(most_recent) +
                '}';
    }
}

