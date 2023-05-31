package com.example.lastdemoMaven.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@RedisHash("sub_categories_data")
public class sub_category_data implements Serializable {


    @Id
    private String sub_category_id;

    private List<PostMetadataEntity> most_viewed;

    private List<PostMetadataEntity> most_recent;

    @Override
    public String toString() {
        return "sub_category_data{" +
                "sub_category_id='" + sub_category_id + '\'' +
                ", most_viewed=" + most_viewed +
                ", most_recent=" + most_recent +
                '}';
    }
}
