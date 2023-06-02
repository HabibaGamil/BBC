package com.feed.feed.dbo.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class RequestFactory {

    private final ObjectMapper objectMapper;


    public Request getRequest(String action,Map<String, Object> map){
        return switch (action.toLowerCase()) {
            case "categoryaction" -> objectMapper.convertValue(map, CategoryRequest.class);
            case "subcategoryaction" -> objectMapper.convertValue(map, SubCategoryRequest.class);
            case "topicaction" -> objectMapper.convertValue(map, TopicRequest.class);
            case "homepageaction" -> objectMapper.convertValue(map, HomePageRequest.class);
            default -> null;
        };

    }


}
