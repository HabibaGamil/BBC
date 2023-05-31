package com.example.lastdemoMaven.dbo;

import com.example.lastdemoMaven.model.PostMetadataEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse implements Serializable {
    private boolean most_viewed;
    private int type;
    private List<String> ids;
    private int number_of_posts;
    private List<List<PostMetadataEntity>> metadata;
}
