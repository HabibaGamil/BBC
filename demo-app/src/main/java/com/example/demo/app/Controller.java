package com.example.demo.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @RequestMapping("/hello")
    public String helloReq(){
        System.out.println("post hello req mapping");
        return "Hello from dummy app";
    }
    @PostMapping("/hello")
    public String helloPost(){
        System.out.println("post hello post mapping");
        return "Hello from dummy app";
    }
    @GetMapping("/hello")
    public String  hello() {
        return "Hello from dummy app";
    }
}
