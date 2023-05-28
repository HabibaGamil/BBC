package com.example.post_mongo.DataClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    String Command;
    Map<String, Object> body;
    Map<String,String> header;
}
