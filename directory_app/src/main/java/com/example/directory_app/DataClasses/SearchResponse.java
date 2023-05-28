package com.example.directory_app.DataClasses;


import com.example.directory_app.entities.PostMetadataEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    private boolean most_viewed;
    private int type;
    private List<String> ids;
    private int number_of_posts;
    private List<PostMetadataEntity> metadata;
}
