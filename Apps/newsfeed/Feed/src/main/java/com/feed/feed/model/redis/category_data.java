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

@RedisHash("Categories_data")
@AllArgsConstructor
@Getter
@Setter
@ToString
public class category_data implements Serializable {


    @Id
    private String category_id;

    // old version JSONObject[] most_viewed;
    private List<PostMetadataEntity> most_viewed;

    // old version JSONObject[] most_recent;
    private List<PostMetadataEntity> most_recent;


}

