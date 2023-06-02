package com.example.post_mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;

@Data
@Document
public class MediaServer {

    @Id
    String id;
    @Indexed(unique = true)
    String mediaUrl;
    String type;
public MediaServer(String mediaUrl, String type){
    this.mediaUrl = mediaUrl;
    this.type = type;
}
    public MediaServer(){
    }
}
