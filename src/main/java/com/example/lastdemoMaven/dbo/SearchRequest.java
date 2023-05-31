package com.example.lastdemoMaven.dbo;

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
public class SearchRequest implements Serializable {
    private boolean most_viewed;
    private int type;
    private int number_of_posts;
}


