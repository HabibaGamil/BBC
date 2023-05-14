package com.example.test_app.controller;

import com.example.test_app.config.Config;
import com.example.test_app.config.CustomClassLoader;
import com.example.test_app.config.Properties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RestController
@EnableAutoConfiguration
@RefreshScope // important
public class TestAppController {
     @Autowired
     Config config;
     Properties properties ;
     RabbitTemplate rabbitTemplate;
     @Autowired
     Environment environment;

    @GetMapping("/user/properties")
    public String getPropertyDetails() throws JsonProcessingException, ClassNotFoundException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(config.getMsg(),config.getCmdMap(),config.getModifiableClasses());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }
    //these endpoints are for test purposes only
    @GetMapping("/hello")
    public String  hello() {
        String port = environment.getProperty("server.port");
        return "Hello from instance running on port " + port;
    }
    @GetMapping("/action")
    public String  performAction(@RequestParam String actionName) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Class clss = properties.getCmdMap().get(actionName);
        Object obj = clss.newInstance();
        Method execute = clss.getMethod("execute");
        execute.invoke(obj);
        return "ok";

    }



}
