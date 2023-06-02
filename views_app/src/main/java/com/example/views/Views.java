package com.example.views;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Views {
    @Id
    private String id;
    @Indexed(unique = true)
    private String postID;
    private int view_count;
public Views (String postID, int view_count){
    this.postID= postID;
    this.view_count= view_count;

}


    public Views() {

    }
}
