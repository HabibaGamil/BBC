package com.generate.generate.controller.redis;



import com.generate.generate.Producer.SearchProducer;
import com.generate.generate.dbo.redis.SearchResponse;
import com.generate.generate.model.redis.category_data;
import com.generate.generate.model.redis.sub_category_data;
import com.generate.generate.model.redis.topic_data;
import com.generate.generate.service.redis.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Slf4j
public class Search_Controller {

    private final SearchProducer searchProducer;
    private final SearchService search_service;

    @GetMapping("/mostViewed")
    public String testSearchMostViewed(){
        searchProducer.send_most_viewed_message();
        return "done mostviewed";

    }

    @GetMapping("/mostRecent")
    public String testSearchMostRecent(){
        searchProducer.send_most_recent_message();
        return "done mostRecent";

    }


   @GetMapping("/category")
    public List<category_data> getAllDataCategoryCache() {
         return search_service.getAllDataCategoryCache();
    }

    @GetMapping("/sub_category")
    public List<sub_category_data> getAllDataSubCategoryCache() {
         return search_service.getAllDataSubCategoryCache();
    }

    @GetMapping("/topic")
    public List<topic_data> getAllDataTopicCache() {
         return search_service.getAllDataTopicCache();
    }
}


//   @PostMapping
//   public SearchResponse process_search_response(@RequestBody SearchResponse search_response){
//      search_service.update_cache(search_response);
//      return search_response;
//   }