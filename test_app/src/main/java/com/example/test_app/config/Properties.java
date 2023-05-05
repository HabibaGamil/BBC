package com.example.test_app.config;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Properties {

    private String msg;
    private Map<String, Class> cmdMap;


    public Properties(String msg, Map<String, String> cmdMapInput) throws ClassNotFoundException {
        this.msg = msg;
        for (String key : cmdMapInput.keySet()){
            String className= cmdMapInput.get(key);
            Class c = Class.forName(className);
            cmdMap.put(key,c);
        }
    }
    public void addCommand (String action, Class c){
        cmdMap.put(action,c);
    }
    public void updateCommand (String action, Class c){
        cmdMap.put(action,c);
    }
    public void deleteCommand(String action){
        cmdMap.remove(action);
    }

}
