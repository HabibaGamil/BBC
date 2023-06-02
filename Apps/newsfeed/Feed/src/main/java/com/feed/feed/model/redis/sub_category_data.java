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

@AllArgsConstructor
@Getter
@Setter
@RedisHash("sub_categories_data")
@ToString
public class sub_category_data implements Serializable {


    @Id
    private String sub_category_id;

    private List<PostMetadataEntity> most_viewed;

    private List<PostMetadataEntity> most_recent;


}