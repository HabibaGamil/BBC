package com.springboot.firstapp.RabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.firstapp.DataClasses.Request;
import com.springboot.firstapp.DataClasses.Response;
import com.springboot.firstapp.Model.CachedUser;
import com.springboot.firstapp.Repository.CachedUserRepository;
import com.springboot.firstapp.configServer.Config;
import com.springboot.firstapp.configServer.Properties;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Method;
import java.util.Map;
//import com.springboot.firstapp.config.MessagingConfig;

@Service
@Configuration
@EnableAutoConfiguration
public class Consumer {
	
	@Autowired
    Config config;
    boolean disabled;
    Properties properties;
    private Logger LOGGER = LoggerFactory.getLogger(Consumer.class);
    
    @Autowired
    AutowireCapableBeanFactory autowireCapableBeanFactory;
    
    @Autowired
    private CachedUserRepository cachedUserRepository;
	
    
    void setProperties () throws ClassNotFoundException {
        if(this.properties==null){
            properties=new Properties(config.getCmdMap());
        }
    }
	
	@RabbitListener(queues = "${rabbitmq.queue.name}")
	public Response consumeMessage(Request request) {
		System.out.println(request.toString());
        System.out.println("command : "+request.getCommand());
        System.out.println("body : "+request.getBody().toString());
        System.out.println("requestParams : "+request.getRequestParameters());
        System.out.println("header : "+request.getHeader().toString());

        Object response = null;
        try {
            setProperties();
            String action = request.getCommand();
            Class c= properties.getCmdMap().get(action);
            System.out.println("ClassName : --> "+c.getName());
            Object object = c.getConstructor().newInstance();
            autowireCapableBeanFactory.autowireBean(object);
            System.out.println("I found the class : " + c.getName());
           // Object obj = c.newInstance();
            Class<?>[] paramTypes = {Map.class, Map.class, Map.class};
            //Method execute = c.getMethod("execute", paramTypes);
            Method execute = c.getDeclaredMethod("execute", paramTypes);
            System.out.println("****");
            
//            for(CachedUser cachedUser : cachedUserRepository.findAll()) {
//    			System.out.println(cachedUser);
//    		}
            
            
            response =  execute.invoke(object, request.getBody(), request.getRequestParameters(), request.getHeader());

        } catch (Exception e) {
            System.out.println(e+" "+e.getCause());
        }

        LOGGER.info(String.format("Request Received from MQ server => %s", request.toString()));
       
        return new Response((String) response);
       
	}
}



