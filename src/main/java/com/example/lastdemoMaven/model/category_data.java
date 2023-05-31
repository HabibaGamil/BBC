package com.example.lastdemoMaven.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@RedisHash("Categories_data")
@AllArgsConstructor
@Getter
@Setter
public class category_data implements Serializable {


    @Id
    private String category_id;

    private List<PostMetadataEntity> most_viewed;

    private List<PostMetadataEntity> most_recent;

    @Override
    public String toString() {
        return "category_data{" +
                "category_id='" + category_id + '\'' +
                ", most_viewed=" + most_viewed +
                ", most_recent=" + most_recent +
                '}';
    }
}

