package com.example.configServer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class configServerController {
    @RequestMapping("/addCommand") public String  addCommand() {
        //eureka server used here to refresh all instances
        return "add command\n";
    }
    @RequestMapping("/deleteCommand") public String deleteCommand() {
        //eureka server used here to refresh all instances
        return "hello\n";
    }
    @RequestMapping("/updateCommand") public String updateCommand() {
        //eureka server used here to refresh all instances
        return "hello\n";
    }
    @RequestMapping("/updateClass") public String updateClass() {
        //eureka server used here to refresh all instances
        return "hello\n";
    }
}
