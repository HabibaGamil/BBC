package com.example.test_app.controller;

import com.example.test_app.TestAppApplication;
import com.example.test_app.config.CustomClassLoader;
import com.example.test_app.config.MyCommand;
import com.example.test_app.config.Properties;
import com.example.test_app.config.TestAppConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.instrument.*;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarFile;

@RestController
@EnableAutoConfiguration
@RefreshScope // important
public class TestAppController {
    @Autowired
    TestAppConfig testConfig;
    Properties classProperties;

    @GetMapping("/testapp/properties")
    public String getPropertyDetails() throws JsonProcessingException, ClassNotFoundException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(testConfig.getMsg(),testConfig.getCmdMap());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }
    @GetMapping("/addCommand")
    public String addCommand ()  {

        CustomClassLoader myLoader = new CustomClassLoader("/C:/SpringProjects/BBC/test_app/target/classes/",ClassLoader.getSystemClassLoader());
        System.out.println("Loader created");
        try{
            MyCommand old = new MyCommand();
            Class command =  myLoader.loadClass("com.example.test_app.config.MyCommand");

            System.out.println("will try to load command");
            Object cmd =  command.newInstance();
            MyCommand command2 = new MyCommand();
            System.out.println("loaded");

        }catch(Exception e){
            System.out.println(e);
            return "class not found";
        }
       return "command added";

    }
//    @PostMapping("/updateClass")
//    @ResponseBody
//    public String updateClass(@RequestParam String className, @RequestParam String directory){
//        String classFile = directory  + className.replace('.','/') + ".class";
//        int classSize = Long.valueOf((new File( classFile )).length()).intValue();
//        byte[] buf = new byte[classSize];
//        try {
//            FileInputStream filein = new FileInputStream( classFile );
//            classSize = filein.read ( buf );
//            filein.close();
//            Class<?> oldClass = Class.forName(className);
//          //  Instrumentation inst ;
//           // Instrumentation instrumentation = InstrumentationAgent.INSTRUMENTATION;
//           redefineClasses(new ClassDefinition(oldClass, buf));
//        } catch(Exception e){
//            return "error";
//        }
//
//        return "method updated successfully";
//
//    }

}
