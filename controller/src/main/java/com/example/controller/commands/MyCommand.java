package com.example.controller.commands;

public class MyCommand implements Command{
    public MyCommand(){

        System.out.println("Old Command Object!!!!!!!!!!!!!");

    }
    public void execute(){

        System.out.println("Executing Old command !");
    }
}
