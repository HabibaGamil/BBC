package org.example.mqServer.DataClasses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Request {
String command;
Map<String, Object> body;
Map<String,String> header;

public Request(String command,Map<String,String> header, String body )  {
    this.command = command;
    this.header = header;
    ObjectMapper objectMapper = new ObjectMapper();
    try {
        this.body = objectMapper.readValue(body, new TypeReference<Map<String, Object>>() {});
    } catch (JsonProcessingException e) {
        e.printStackTrace();
    }
}
}
