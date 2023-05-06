package com.example.test_app.commands;

public class TestCommand implements Command{

    public TestCommand(){
        System.out.println("New Test Command!");
    }
    @Override
    public void execute() {
        System.out.println("Executing Test Command!");
    }
}
