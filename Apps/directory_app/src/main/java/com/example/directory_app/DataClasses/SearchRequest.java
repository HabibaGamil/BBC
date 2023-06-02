package com.example.directory_app.DataClasses;

import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest{
    private boolean most_viewed;
    private int type;
    private List<String> ids;
    private int number_of_posts;
}