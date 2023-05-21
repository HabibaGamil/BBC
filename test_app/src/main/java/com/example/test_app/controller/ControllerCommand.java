package com.example.test_app.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControllerCommand{
    public String command;
    String actionName;
    byte[] bytes;
}
