package com.springboot.firstapp.Controller;

import com.springboot.firstapp.RabbitMQ.RabbitMQConfig;
//import com.springboot.firstapp.config.MessagingConfig;
import com.springboot.firstapp.configServer.Config;
import com.springboot.firstapp.configServer.Properties;
import com.springboot.firstapp.configServer.CustomClassLoader;

import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

//import org.example.mqServer.RabbitMQ.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

@RestController
@EnableAutoConfiguration
@RefreshScope // important
@RequiredArgsConstructor
@Service
public class TestAppController {
     @Autowired
     Config config;
     Properties properties ;
     //RabbitTemplate rabbitTemplate;
     @Autowired
     Environment environment;
     
     //final private RabbitTemplate template;
     final private RabbitTemplate rabbitTemplate;
     @Autowired 
     RabbitMQConfig rabbitMQConfig;
     
//     public TestAppController(RabbitTemplate rabbitTemplate) {
//         this.rabbitTemplate = rabbitTemplate;
//     }

    @GetMapping("/app/properties")
    public String getPropertyDetails() throws JsonProcessingException, ClassNotFoundException {
    	System.out.println(config.getCmdMap().toString());
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(config.getCmdMap());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }

    @GetMapping("/hello")
    public String  hello() {
        String port = environment.getProperty("server.port");
        return "Hello from instance running on port " + port;
    }


	
	@PostMapping("/dummy")
	public String testRabbit(@RequestParam(name = "word") String word) {
		rabbitTemplate.convertAndSend("user_newfeed_exchange", "youssef",word);
		return "alhamdollelah";
	}

}