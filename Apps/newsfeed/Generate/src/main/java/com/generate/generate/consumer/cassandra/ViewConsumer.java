package com.generate.generate.consumer.cassandra;


import com.generate.generate.dbo.cassandra.ViewRequest;
import com.generate.generate.service.cassandra.ViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Service
@RequiredArgsConstructor
@Slf4j
public class ViewConsumer {
    private final ViewService updateService;

    @RabbitListener(queues = "${queue.newsfeed.view.userViewPost}")
    public void update(@RequestBody ViewRequest viewRequest) {
        log.info("consumed");
        updateService.upadteViewCount(viewRequest);
    }

//    @GetMapping("/test")
//    public List<user_category> test(){
//
//        return updateService.test();
//
//    }


}
