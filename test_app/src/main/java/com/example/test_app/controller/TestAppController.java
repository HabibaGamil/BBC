package com.example.test_app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAppController {

    @RequestMapping("/") public String hello() {
        return "hello\n";
    }


}
