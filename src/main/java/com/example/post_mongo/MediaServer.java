package com.example.post_mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

@Data
public class MediaServer {

    @Id
    String id;
    String mediaUrl;
    String type;
public MediaServer(String mediaUrl, String type){
    this.mediaUrl = mediaUrl;
    this.type = type;
}
    public MediaServer(){
    }
}
