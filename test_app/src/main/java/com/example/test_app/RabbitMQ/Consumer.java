package com.example.test_app.RabbitMQ;


import com.example.test_app.DataClasses.Request;
import com.example.test_app.DataClasses.Response;
import com.example.test_app.config.Config;
import com.example.test_app.config.CustomClassLoader;
import com.example.test_app.config.Properties;
import com.example.test_app.controller.ControllerCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Service
@Configuration
@EnableAutoConfiguration
//@EnableConfigurationProperties(Config.class)
public class Consumer {

    @Autowired
    Config config;
    boolean disabled;
    Properties properties;
    private Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    void setProperties () throws ClassNotFoundException {
        if(this.properties==null){
            properties=new Properties(config.getMsg(),config.getCmdMap(),config.getModifiableClasses());
        }
    }
    @RabbitListener(queues = "${rabbitmq.queue.mqServer.name}")
    public Response consumeMQServerMessage(Request request){
        try {
            setProperties();
            String action = request.getCommand();
            Class c= properties.getCmdMap().get(action);
            Object obj = c.newInstance();
            Method execute = c.getMethod("execute");
            execute.invoke(obj);

        } catch (Exception e) {
            System.out.println(e);
        }

        LOGGER.info(String.format("Request Received from MQ server => %s", request.toString()));
        return new Response("The MQ server Request is received to test app, this is the test app response.");
    }

    @RabbitListener(queues = "#{controllerQueue.name}")
    public void consume(ControllerCommand controllerCommand){

        String command = controllerCommand.getCommand();
        String actionName = controllerCommand.getActionName();
        try {

            setProperties();
            if (command.equals("addCommand")) {
                byte[] bytes = controllerCommand.getBytes();
                CustomClassLoader loader = new CustomClassLoader(ClassLoader.getSystemClassLoader(), bytes);
                Class c = loader.loadClass("");
                this.properties.addCommand(actionName,c);
                LOGGER.info(String.format("I RECIEVED FROM fanout!!"));

            }
            if (command.equals("updateCommand")) {
                byte[] bytes = controllerCommand.getBytes();
                CustomClassLoader loader = new CustomClassLoader(ClassLoader.getSystemClassLoader(), bytes);
                Class c = loader.loadClass("");
                this.properties.addCommand(actionName,c);

            }
            if (command.equals("deleteCommand")) {
              this.properties.deleteCommand(actionName);

                Class cls =this.properties.getCmdMap().get(actionName);
                System.out.println("class is:"+cls);

            }
            if (command.equals("updateClass")) {

            }
            if (command.equals("freeze")) {
                disabled = true;

            }
            if (command.equals("continue")) {
                disabled = false;

            }
            if (command.equals("setThreadCount")) {

            }
        }
        catch(Exception e){
            System.out.println(e);
        }
       // LOGGER.info(String.format("Controller command received in test_app service => %s", controllerCommand.toString()));
    }
}