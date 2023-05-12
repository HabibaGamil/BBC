package com.example.lastdemoMaven.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Arrays;


@Getter
@Setter
@AllArgsConstructor
@RedisHash("topics_data")
public class topic_data implements Serializable {


    @Id
    private String topic_id;

    private String topic_name;

    private meta_data [] most_viewed;

    private meta_data [] most_recent;

    @Override
    public String toString() {
        return "topic_data{" +
                "topic_id='" + topic_id + '\'' +
                ", topic_name='" + topic_name + '\'' +
                ", most_viewed=" + Arrays.toString(most_viewed) +
                ", most_recent=" + Arrays.toString(most_recent) +
                '}';
    }
}
