package com.example.lastdemoMaven.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// post data is an object that will contain a key and metadata
// the key represents the category of metadata we have
// the list will have meta data that need to be added immediately
// string of post_meta_data will be string hat contains JSONObject

@Getter
@Setter
@AllArgsConstructor
public class post_data {


    private String key;
    private meta_data post_meta_data;

    @Override
    public String toString() {
        return "post_data{" +
                "key='" + key + '\'' +
                ", post_meta_data=" + post_meta_data +
                '}';
    }
}
