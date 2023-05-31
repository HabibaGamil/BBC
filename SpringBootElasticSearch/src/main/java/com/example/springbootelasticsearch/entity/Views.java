package com.example.springbootelasticsearch.entity;
import lombok.Data;


@Data
public class Views {

    private String id;
    private String postID;
    private int view_count;
    public Views(String postID, int view_count) {
        this.postID = postID;
        this.view_count = view_count;

    }

    public Views() {

    }
}
