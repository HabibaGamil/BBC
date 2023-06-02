package com.generate.generate.controller.cassandra;


import com.generate.generate.dbo.cassandra.ViewRequest;
import com.generate.generate.test.ViewProducerTest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import com.generate.generate.service.cassandra.ViewService;
@RestController
@RequestMapping("/api/view")
@RequiredArgsConstructor
@Slf4j
public class ViewController {
    //private final ViewService updateService;
    private final ViewProducerTest viewProducerTest;

    @GetMapping("/test")
    public ViewRequest testView(){
        ViewRequest viewRequest = viewProducerTest.publishView();
        return viewRequest;
    }





//    @GetMapping("/test")
//    public List<user_category> test(){
//
//        return updateService.test();
//
//    }


}



//public class ViewController {
//    private final ViewService updateService;
//
//
//    @PostMapping
//    public String update(@RequestBody ViewRequest viewRequest) {
//        log.info("updating");
//        updateService.upadteViewCount(viewRequest);
//        return "updated";
//    }
//
////    @GetMapping("/test")
////    public List<user_category> test(){
////
////        return updateService.test();
////
////    }
//
//
//}
