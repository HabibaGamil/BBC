package com.example.test_app.controller;

import com.example.test_app.TestAppApplication;
import com.example.test_app.config.CustomClassLoader;
import com.example.test_app.config.MyCommand;
import com.example.test_app.config.Properties;
import com.example.test_app.config.TestAppConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@EnableAutoConfiguration
@RefreshScope // important
public class TestAppController {
    @Autowired
    TestAppConfig testConfig;

    @Value("${server.instance.id}")
    String instanceId;
    @GetMapping("/hello")
    public String hello() {
        return String.format("Hello from instance %s", instanceId);
    }

    @GetMapping("/testapp/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(testConfig.getMsg(),testConfig.getCmdMap());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }
    @GetMapping("/addCommand")
    public String addCommand ()  {
        System.out.println("In method");
        CustomClassLoader myLoader = new CustomClassLoader("/C:/SpringProjects/BBC/test_app/target/classes/",ClassLoader.getSystemClassLoader());
        System.out.println("Loader created");
        try{
          Class command =  myLoader.loadClass("com.example.test_app.config.NewCommand");
            System.out.println("will try to load command");
          Object cmd =  command.newInstance();
            System.out.println("loaded");

        }catch(Exception e){
            System.out.println(e);
            return "class not found";
        }
       return "command added";

    }



}
