package com.example.lastdemoMaven.controller;


import com.example.lastdemoMaven.Producer.RabbitMQ_Search_Producer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/newsfeed_search")
public class Message_Controller {

    private RabbitMQ_Search_Producer the_producer;

    public Message_Controller(RabbitMQ_Search_Producer the_producer) {
        this.the_producer = the_producer;
    }


    @GetMapping("/sending_request")
    public ResponseEntity<String> send_message(){
        the_producer.send_most_viewed_message();
        the_producer.send_most_recent_message();
        return ResponseEntity.ok("message sent to rabbitMQ");
    }

    @GetMapping("/test")
    public String test(){
        return "done";
    }

}
