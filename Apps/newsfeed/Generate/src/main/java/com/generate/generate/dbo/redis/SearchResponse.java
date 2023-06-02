package com.generate.generate.dbo.redis;


import com.generate.generate.model.redis.PostMetadataEntity;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchResponse implements Serializable {

    private boolean most_viewed;
    private int type;
    private List<String> ids;
    private int number_of_posts;
    private List<List<PostMetadataEntity>> metadata;


}
