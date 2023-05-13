package com.example.controller;

import com.example.controller.RabbitMQ.ControllerProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class Controller {
    private ControllerProducer controllerProducer;

    public Controller(ControllerProducer controllerProducer)
    {
        this.controllerProducer = controllerProducer;
    }
    @PostMapping("/addCommand")
    public String addCommand(@RequestParam String className){
        System.out.println(className);
        BytesLoader loader = new BytesLoader();
        try {
            byte[] bytes = loader.getBytesArray(className);
            System.out.println("length: "+bytes.length);
            controllerProducer.sendMessage(new ControllerCommand("AddCommand", bytes));

        }catch (Exception e){
            System.out.println(e);
            return "an error occurred";
        }
        return "Command Added!";
    }
    @PostMapping("/updateCommand")
    public String updateCommand(@RequestParam String className){
        return "Command Updated!";
    }
    @PostMapping("/deleteCommand")
    public String deleteCommand(@RequestParam String className){
        return "Command Deleted";
    }
    @PostMapping("/updateClass")
    public String updateClass(@RequestParam String className){
        return "Class Updated!";
    }



}
