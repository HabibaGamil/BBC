package com.example.test_app.controller;


import com.example.test_app.RabbitMQ.RequestResponse;
import com.example.test_app.config.Config;
import com.example.test_app.config.CustomClassLoader;
import com.example.test_app.config.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

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
    @RabbitListener(queues = "${rabbitmq.queue.controller.name}")
    public RequestResponse consume(ControllerCommand controllerCommand){

        String command = controllerCommand.getCommand();
        String actionName = controllerCommand.getActionName();
        try {

            setProperties();
            if (command.equals("addCommand")) {
                byte[] bytes = controllerCommand.getBytes();
                CustomClassLoader loader = new CustomClassLoader(ClassLoader.getSystemClassLoader(), bytes);
                Class c = loader.loadClass("");
                this.properties.addCommand(actionName,c);

//                Class cls =this.properties.getCmdMap().get(actionName);
//                Object obj = cls.newInstance();



            }
            if (command.equals("updateCommand")) {
                byte[] bytes = controllerCommand.getBytes();
                CustomClassLoader loader = new CustomClassLoader(ClassLoader.getSystemClassLoader(), bytes);
                Class c = loader.loadClass("");
                this.properties.addCommand(actionName,c);

                Class cls =this.properties.getCmdMap().get(actionName);
                Object obj = cls.newInstance();

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
        LOGGER.info(String.format("Controller command received in test_app service => %s", controllerCommand.toString()));
        return new RequestResponse("The controller command is received to test app, this is the test app response.");
    }
}