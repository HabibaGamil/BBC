package com.example.test_app.controller;

import com.example.test_app.DataClasses.Request;
import com.example.test_app.DataClasses.Response;
import com.example.test_app.RabbitMQ.Producer;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

@RestController
@EnableAutoConfiguration
public class TestAppController {
    private Producer producer;
    @Autowired
     Config config;

     @Value("${testapp.msg}")
     String message;
     Properties properties ;
     RabbitTemplate rabbitTemplate;
     @Autowired
     Environment environment;

    @GetMapping("/app/properties")
    public String getPropertyDetails() throws JsonProcessingException, ClassNotFoundException {
        System.out.println(message);
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

    @GetMapping("/test")
    public String  test() {
        HashMap<String,Object> testMapheaders = new HashMap<>();
        HashMap<String,String> testMapBody = new HashMap<>();

        Request r = new Request("test",testMapheaders,testMapBody);
        Response res = producer.sendMessage(r);
        return  res.getResponse();
    }


}
