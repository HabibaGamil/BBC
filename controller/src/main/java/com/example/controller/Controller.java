package com.example.controller;

import com.example.controller.RabbitMQ.ControllerProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private ControllerProducer controllerProducer;

    public Controller(ControllerProducer controllerProducer)
    {
        this.controllerProducer = controllerProducer;
    }
    @PostMapping("/addCommand")
    public String addCommand(@RequestParam String className, @RequestParam String actionName){
        BytesLoader loader = new BytesLoader();
        try {
            byte[] bytes = loader.getBytesArray(className);
            controllerProducer.sendMessage(new ControllerCommand("addCommand",actionName, 0,bytes));
        }catch (Exception e){
            System.out.println(e);
            return "an error occurred";
        }
        return "Apps notified to add command!";
    }
    @PostMapping("/updateCommand")
    public String updateCommand(@RequestParam String className, @RequestParam String actionName){

        BytesLoader loader = new BytesLoader();
        try {
            byte[] bytes = loader.getBytesArray(className);
            controllerProducer.sendMessage(new ControllerCommand("updateCommand",actionName,0, bytes));

        }catch (Exception e){
            System.out.println(e);
            return "an error occurred";
        }
        return "Apps notified to Update command!";
    }
    @PostMapping("/deleteCommand")
    public String deleteCommand(@RequestParam String className, @RequestParam String actionName){
        BytesLoader loader = new BytesLoader();
        try {
            byte[] bytes = loader.getBytesArray(className);
            controllerProducer.sendMessage(new ControllerCommand("deleteCommand", actionName, 0,bytes));

        }catch (Exception e){
            System.out.println(e);
            return "an error occurred";
        }
        return "Apps notified to delete command!";
    }
    @PostMapping("/updateClass")
    public String updateClass(@RequestParam String className){
        BytesLoader loader = new BytesLoader();
        try {
            byte[] bytes = loader.getBytesArray(className);
            controllerProducer.sendMessage(new ControllerCommand("updateClass",null, 0,bytes));

        }catch (Exception e){
            System.out.println(e);
            return "an error occurred";
        }
        return "Apps notified to update class!";
    }

    @PostMapping("/freeze")
    public String freezeApp(){
        try {
            controllerProducer.sendMessage(new ControllerCommand("freeze",null, 0,null));

        }catch (Exception e){
            System.out.println(e);
            return "an error occurred";
        }
        return "App Freezed!";
    }
    @PostMapping("/continue")
    public String resumeApp(){
        try {
            controllerProducer.sendMessage(new ControllerCommand("continue",null, 0,null));

        }catch (Exception e){
            System.out.println(e);
            return "an error occurred";
        }
        return "App Resumed!";
    }
    @PostMapping("/setThreadCount")
    public String setAppThreadCount(@RequestParam int count){
        try {
            controllerProducer.sendMessage(new ControllerCommand("setThreadCount",null, count,null));

        }catch (Exception e){
            System.out.println(e);
            return "an error occurred";
        }
        return "App Thread count set!";
    }
    @PostMapping("/setMaxDbConnections")
    public String setAppMaxDbConnections(@RequestParam int count){
        try {
            controllerProducer.sendMessage(new ControllerCommand("setThreadCount",null, count,null));

        }catch (Exception e){
            System.out.println(e);
            return "an error occurred";
        }
        return "Max DB connections set!";
    }



}
