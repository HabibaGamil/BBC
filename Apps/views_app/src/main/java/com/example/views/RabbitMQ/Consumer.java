package com.example.views.RabbitMQ;

import com.example.views.DataClasses.UpdateViewCountRequest;
import com.example.views.config.Config;
import com.example.views.config.Properties;
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


    void setProperties() throws ClassNotFoundException {
        if (this.properties == null) {
            properties = new Properties(config.getMsg(), config.getCmdMap(), config.getModifiableClasses());
        }
    }


    @RabbitListener(queues = "post_views_queue")
    public void updateViewsconsumeMQSerrverMessage(UpdateViewCountRequest req) {
        try {
            setProperties();
            String action = "UpdateViewsAction";
            Class c = properties.getCmdMap().get(action);
            Object obj = c.getConstructor().newInstance();
            Method execute = c.getMethod("execute", String.class);
            execute.invoke(obj, req.getPostMetadataId());
        } catch (Exception e) {
            System.out.println(e);
        }

        LOGGER.info(String.format("Request Received from MQ server and updated"));
    }

}