package com.example.test_app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAppController {

    @RequestMapping("/addCommand") public String  addCommand() {
        return "add command\n";
    }
    @RequestMapping("/deleteCommand") public String deleteCommand() {
        return "hello\n";
    }
    @RequestMapping("/updateCommand") public String updateCommand() {
        return "hello\n";
    }
    @RequestMapping("/updateClass") public String updateClass() {

        return "hello\n";
    }


}
