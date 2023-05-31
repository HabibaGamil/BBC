package com.example.springbootelasticsearch.commands;

public class DummyCommand {
    public DummyCommand(){
        System.out.println("New Dummy Command!");
    }
    public void execute() {
        System.out.println("Executing Dummy Command!");
    }
}
