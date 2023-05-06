package com.example.test_app.commands;

public class DummyCommand implements Command{
    public DummyCommand(){
        System.out.println("New Dummy Command!");
    }
    @Override
    public void execute() {
        System.out.println("Executing Dummy Command!");
    }
}
