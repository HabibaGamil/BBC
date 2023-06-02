package com.feed.feed.commands;

import com.feed.feed.dbo.request.HomePageRequest;
import com.feed.feed.dbo.request.Request;
import com.feed.feed.dbo.response.HomePageResponse;
import com.feed.feed.model.cassandra.UserPreferences;
import com.feed.feed.model.cassandra.UserSubscriptions;
import com.feed.feed.model.redis.category_data;
import com.feed.feed.model.redis.topic_data;
import com.feed.feed.repository.cassandra.UserPreferencesRepository;
import com.feed.feed.repository.cassandra.UserSubscriptionsRepository;
import com.feed.feed.repository.redis.posts_repository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.feed.feed.repository.redis.categories_repository;
import com.feed.feed.repository.redis.topics_repository;

import java.util.*;


@Service
@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class HomePageCommand implements Command{
    private final UserSubscriptionsRepository userSubscriptionsRepository;

    private final UserPreferencesRepository userPreferencesRepository;
    private final categories_repository CR;
    private final topics_repository TD;
    private final posts_repository PR;
    private final int countNumberPosts = 10;


    @Override
    public HomePageResponse execute(Request request) {
        HomePageRequest requestCast = (HomePageRequest) request;
        String userId = requestCast.getUserId();
        HomePageResponse response = new HomePageResponse();
        response.setUserId(userId);
        response.setPreferences(userPreferences(userId));
        response.setSubscriptions(userSubscriptions(userId));
        response.setNews(trends());
        return response;


    }

    public List<Object> trends(){
        return new ArrayList<>(PR.get_posts_hash().values());
    }

    public List<category_data>userPreferences(String userId){
        Optional<UserPreferences> preferences = userPreferencesRepository.findById(userId);
        if(preferences.isPresent()){
            UserPreferences preferencesCategories = preferences.get();
           return (List<category_data>)CR.findAllById(preferencesCategories.getPreferences());
        }
        else{
          return getRecords(countNumberPosts);

        }

    }

    public List<topic_data> userSubscriptions(String userId)
    {
        Optional<UserSubscriptions> subscriptions = userSubscriptionsRepository.findById(userId);
        if(subscriptions.isPresent()){
            UserSubscriptions subscriptionTopics = subscriptions.get();
            return (List<topic_data>)TD.findAllById(subscriptionTopics.getTopicsId());
        }
        else{
            return new ArrayList<>();
        }
    }


    private  List<category_data> getRecords(int count){

        List<category_data> allRecords = (List<category_data>) CR.findAll();
        count = Math.min(count,allRecords.size());
        return allRecords.subList(0,count);
    }

//    private List<category_data> getRecords(int count) {
//        List<category_data> allRecords = (List<category_data>) CR.findAll();
//
//        if (allRecords.size() <= count) {
//            return allRecords;
//        }
//
//        List<category_data> randomRecords = new ArrayList<>();
//        Set<Integer> indexes = new HashSet<>();
//
//        Random random = new Random();
//        while (indexes.size() < count) {
//            int index = random.nextInt(allRecords.size());
//            if (indexes.add(index)) {
//                randomRecords.add(allRecords.get(index));
//            }
//        }
//
//        return randomRecords;
//    }
}
