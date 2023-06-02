package com.feed.feed.model.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@RedisHash("topics_data")
@ToString
public class topic_data implements Serializable {


    @Id
    private String topic_id;

    private List<PostMetadataEntity> most_viewed;

    private List<PostMetadataEntity> most_recent;


}
