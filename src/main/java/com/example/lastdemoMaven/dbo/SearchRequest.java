package com.example.lastdemoMaven.dbo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest{
    private boolean most_viewed;
    private int type;
    private List<String> ids;
    private int number_of_posts;
}


