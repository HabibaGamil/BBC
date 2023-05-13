package org.example.mqServer.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@EnableAutoConfiguration
@RefreshScope // important
public class MQServerController {

    @Autowired
    Environment environment;

    @RequestMapping({"/{request}"})
    public String  hello(@PathVariable String request) {

        String port = environment.getProperty("server.port");
        return "Hello from instance running on port " + port + " , request "+ request;
    }


}
