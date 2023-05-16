package com.example.controller;

import com.example.controller.RabbitMQ.ControllerProducer;
import com.example.controller.RabbitMQ.Response;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    private ControllerProducer controllerProducer;

    public Controller(ControllerProducer controllerProducer)
    {
        this.controllerProducer = controllerProducer;
    }
    @PostMapping("{app}/addCommand")
    public String addCommand(@RequestParam String className, @RequestParam String actionName, @PathVariable String app){
        BytesLoader loader = new BytesLoader();
        try {
            byte[] bytes = loader.getBytesArray(className);
           controllerProducer.sendMessage(new ControllerCommand("addCommand",actionName, 0,bytes),app);
        }catch (Exception e){
            System.out.println(e);
            return "an error occurred";
        }
        return "Apps notified to add command!";
    }
    @PostMapping("{app}/updateCommand")
    public String updateCommand(@RequestParam String className, @RequestParam String actionName, @PathVariable String app){

        BytesLoader loader = new BytesLoader();
        try {
            byte[] bytes = loader.getBytesArray(className);
            controllerProducer.sendMessage(new ControllerCommand("updateCommand",actionName,0, bytes),app);

        }catch (Exception e){
            System.out.println(e);
            return "an error occurred";
        }
        return "Apps notified to Update command!";
    }
    @PostMapping("{app}/deleteCommand")
    public String deleteCommand(@RequestParam String className, @RequestParam String actionName, @PathVariable String app){
        BytesLoader loader = new BytesLoader();
        try {
            byte[] bytes = loader.getBytesArray(className);
            controllerProducer.sendMessage(new ControllerCommand("deleteCommand", actionName, 0,bytes),app);

        }catch (Exception e){
            System.out.println(e);
            return "an error occurred";
        }
        return "Apps notified to delete command!";
    }
    @PostMapping("{app}/updateClass")
    public String updateClass(@RequestParam String className, @PathVariable String app){
        BytesLoader loader = new BytesLoader();
        try {
            byte[] bytes = loader.getBytesArray(className);
            controllerProducer.sendMessage(new ControllerCommand("updateClass",null, 0,bytes),app);

        }catch (Exception e){
            System.out.println(e);
            return "an error occurred";
        }
        return "Apps notified to update class!";
    }

    @PostMapping("{app}/freeze")
    public String freezeApp(@PathVariable String app){
        try {
            controllerProducer.sendMessage(new ControllerCommand("freeze",null, 0,null),app);

        }catch (Exception e){
            System.out.println(e);
            return "an error occurred";
        }
        return "App Freezed!";
    }
    @PostMapping("{app}/continue")
    public String resumeApp(@PathVariable String app){
        try {
            controllerProducer.sendMessage(new ControllerCommand("continue",null, 0,null),app);

        }catch (Exception e){
            System.out.println(e);
            return "an error occurred";
        }
        return "App Resumed!";
    }
    @PostMapping("{app}/setThreadCount")
    public String setAppThreadCount(@RequestParam int count, @PathVariable String app){
        try {
            controllerProducer.sendMessage(new ControllerCommand("setThreadCount",null, count,null),app);

        }catch (Exception e){
            System.out.println(e);
            return "an error occurred";
        }
        return "App Thread count set!";
    }
    @PostMapping("{app}/setMaxDbConnections")
    public String setAppMaxDbConnections(@RequestParam int count, @PathVariable String app){
        try {
            controllerProducer.sendMessage(new ControllerCommand("setThreadCount",null, count,null),app);

        }catch (Exception e){
            System.out.println(e);
            return "an error occurred";
        }
        return "Max DB connections set!";
    }



}
