package com.example.lastdemoMaven.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@RedisHash("topics_data")
public class topic_data implements Serializable {


    @Id
    private String topic_id;

    private List<PostMetadataEntity> most_viewed;

    private List<PostMetadataEntity> most_recent;

    @Override
    public String toString() {
        return "topic_data{" +
                "topic_id='" + topic_id + '\'' +
                ", most_viewed=" + most_viewed +
                ", most_recent=" + most_recent +
                '}';
    }
}
