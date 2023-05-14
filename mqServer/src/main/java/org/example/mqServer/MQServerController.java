package org.example.mqServer;

import org.example.mqServer.DataClasses.Response;
import org.example.mqServer.RabbitMQ.Producer;
import jakarta.servlet.http.HttpServletResponse;
import org.example.mqServer.DataClasses.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


@RestController
//@EnableAutoConfiguration
//@RefreshScope // important
public class MQServerController {
    private Producer producer;
    private Logger LOGGER = LoggerFactory.getLogger(MQServerController.class);

    public MQServerController(Producer producer)
    {
        this.producer = producer;
    }

    @Autowired
    Environment environment;

    @RequestMapping({"{app}/{requestParams}"})

    public Response getHandler(@PathVariable String requestParams, @PathVariable String app, @RequestHeader Map<String, String> headers, HttpServletResponse servletResponse) {

        Request request = new Request(requestParams,headers);
        Response response = producer.sendMessage(request,app);
        if(response == null)
            servletResponse.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        return response;
    }

    @PostMapping("{app}/{requestParameters}")
    public Response handler(@RequestBody Map<String, Object> body,@PathVariable String requestParameters,@PathVariable String app,
                                       @RequestHeader Map<String, String> headers, HttpServletResponse servletResponse) {
        Request request = new Request(requestParameters,body,headers);
      Response response = producer.sendMessage(request,app);
        if(response == null)
            servletResponse.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        return response;
    }


    }
