package com.example.test_app.controller;

import com.example.test_app.config.Properties;
import com.example.test_app.config.Config;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@EnableAutoConfiguration
@RefreshScope // important
public class TestAppController {
    @Autowired

    Config config;
    Properties classProperties;

    @Autowired
    Environment environment;

    @GetMapping("/user/properties")
    public String getPropertyDetails() throws JsonProcessingException, ClassNotFoundException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(config.getMsg(),config.getCmdMap(),config.getModifiableClasses());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }

    @GetMapping("/hello")
    public String  hello() {

        String port = environment.getProperty("server.port");
        return "Hello from instance running on port " + port;
    }


}
