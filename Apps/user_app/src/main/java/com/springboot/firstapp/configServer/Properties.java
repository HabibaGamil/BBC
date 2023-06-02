package com.springboot.firstapp.configServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import java.lang.reflect.*;

@Getter
@Setter
public class Properties {

    //private String msg;
    //This map saves all action/command pairings in the system
    private Map<String, Class> cmdMap;
    //This map saves each modifiable class name and its corresponding abstract factory
    //private Map<String, Class> modifiableClasses;

    public Properties(Map<String, String> cmdMapInput) 
    							throws ClassNotFoundException {
      //  this.msg = msg;
        this.cmdMap=new HashMap<>();
      //  this.modifiableClasses=new HashMap<>();

        for (String key : cmdMapInput.keySet()){
            String className= cmdMapInput.get(key);
            Class <?> c = Class.forName("com.springboot.firstapp.Commands."+className);
            System.out.println(key + " : "+className +" = "+c.getName());
            cmdMap.put(key,c);
        }
   
        System.out.println(cmdMap.toString());
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
    public void updateClass(String action){

    }

}
