package com.example.test_app.controller;

import com.example.test_app.Properties;
import com.example.test_app.TestAppConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@EnableAutoConfiguration
@RefreshScope // important
public class TestAppController {


    @Autowired
    TestAppConfig testConfig;

    @GetMapping("/testapp/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(testConfig.getMsg(),testConfig.getCmdMap());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }


}
