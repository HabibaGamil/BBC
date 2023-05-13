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
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RefreshScope // important
public class TestAppController {
    @Autowired

    Config config;
    Properties classProperties;
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

    @GetMapping("/hello")
    public String  hello() {

        String port = environment.getProperty("server.port");
        return "Hello from instance running on port " + port;
    }
    @RabbitListener(queues = "test")
    public void updateCommand(Message msg) throws ClassNotFoundException {
        byte [] bytes = msg.getBody();
        System.out.println("received "+bytes.length);
        //  ControllerCommand controllerCommand = (ControllerCommand) SerializationUtils.deserialize(bytes);
        System.out.println(bytes.length);
        CustomClassLoader loader = new CustomClassLoader(ClassLoader.getSystemClassLoader(), bytes);
        try
        {
            System.out.println("trying to load the class");
            Class Command = loader.loadClass("MyCommand");
            System.out.println("Class loaded");
            Object commandNewInstance = Command.newInstance();

        }
        catch (ClassNotFoundException ex)
        {
            System.out.println("Class not found" + ex);
        } catch (InstantiationException e) {
            System.out.println("InstantiationException"+ e);
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException"+e);
        }
        System.out.println("Received!!!!!!!!!!!! ");
    }


}
