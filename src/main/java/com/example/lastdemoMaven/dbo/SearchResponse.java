package com.example.lastdemoMaven.dbo;

import com.example.lastdemoMaven.model.meta_data;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    private boolean most_viewed;
    private int type;
    private String [] ids;
    private int number_of_posts;
    private List<meta_data[]> metadata;


}
