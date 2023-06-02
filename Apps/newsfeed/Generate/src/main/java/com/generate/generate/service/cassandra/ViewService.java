package com.generate.generate.service.cassandra;


import com.generate.generate.dbo.cassandra.ViewRequest;
import com.generate.generate.model.cassandra.UserCategory;
import com.generate.generate.model.cassandra.UserPreferences;
import com.generate.generate.repository.cassandra.UserCategoryRepository;
import com.generate.generate.repository.cassandra.UserPreferencesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.support.locks.ExpirableLockRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;


@Service
@RequiredArgsConstructor
@Slf4j
public class ViewService {

    private final HashMap<String,HashMap<String,Integer>> categoryMap = new HashMap<>();

    private final UserCategoryRepository userCategoryRepository;

    private final UserPreferencesRepository userPreferencesRepository;


    private final ExpirableLockRegistry lockRegistry;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");



    private static final String lockKey = "updateTaskToDatabase";

    private static final long lockTimeout = 1000L;

    private static final int numberOfRetries = 10;

    private static final int timeToUpdateMap = 15000;


    public void upadteViewCount(ViewRequest viewRequest){
        if(categoryMap.containsKey(viewRequest.getUserId())){
            if(categoryMap.get(viewRequest.getUserId()).containsKey(viewRequest.getCategoryId())){
                categoryMap.get(viewRequest.getUserId()).put(viewRequest.getCategoryId(),categoryMap.get(viewRequest.getUserId()).get(viewRequest.getCategoryId())+1);
            }
            else{
                categoryMap.get(viewRequest.getUserId()).put(viewRequest.getCategoryId(), 1);
            }

        }
        else {
            HashMap<String,Integer> categoryCount = new HashMap<>();
            categoryCount.put(viewRequest.getCategoryId(), 1);
            categoryMap.put(viewRequest.getUserId(),categoryCount);

        }

    }


    private List<UserCategory> getUserCategory(){
        List<String> ids = categoryMap.keySet().stream().toList();
        Set<String> setIds = new HashSet<>();
        List<UserCategory> user_category_data = userCategoryRepository.findAllById(ids);
        for(UserCategory userCategory : user_category_data){
            setIds.add(userCategory.getUserId());
        }
        for(String id : ids){
            if(!setIds.contains(id)){
                UserCategory userCategory = new UserCategory();
                userCategory.setUserId(id);
                Map<String,Integer> categoryCount = new HashMap<>();
                userCategory.setCategoryCount(categoryCount);
                user_category_data.add(userCategory);
            }

        }
        return user_category_data;
    }

    private Map<String,UserPreferences> getUserPreferences(){
        Map<String,UserPreferences> userPreferencesMap = new HashMap<>();
        List<String> ids = categoryMap.keySet().stream().toList();
        List<UserPreferences> userPreferencesData = userPreferencesRepository.findAllById(ids);
        for(UserPreferences userPreferences : userPreferencesData){
            userPreferencesMap.put(userPreferences.getUserId(),userPreferences);
        }
        for(String id : ids){
            if(!userPreferencesMap.containsKey(id)){
                UserPreferences userPreferences = new UserPreferences(id,new ArrayList<>());
                userPreferencesMap.put(userPreferences.getUserId(),userPreferences);
            }

        }
        return userPreferencesMap;

    }



    private void updateTaskToDatabase(){
        List<UserCategory> userCategoryList = getUserCategory();
        Map<String, UserPreferences> userPreferencesMap = getUserPreferences();
        for(UserCategory userCategory : userCategoryList){
            String userId = userCategory.getUserId();
            for(String categoryId : categoryMap.get(userId).keySet()){
                if(userCategory.getCategoryCount().containsKey(categoryId)){
                    userCategory.getCategoryCount().put(categoryId,userCategory.getCategoryCount().get(categoryId)+categoryMap.get(userId).get(categoryId));
                }
                else{
                    userCategory.getCategoryCount().put(categoryId, categoryMap.get(userId).get(categoryId));
                }
            }
            List<String> sortedCategoriesIds = getKeysSortedByValues(userCategory.getCategoryCount());

            userPreferencesMap.get(userId).setPreferences(sortedCategoriesIds);


        }
        categoryMap.clear();

        userPreferencesRepository.saveAll(userPreferencesMap.values().stream().toList());
        userCategoryRepository.saveAll(userCategoryList);

    }
    @Scheduled(fixedRate = timeToUpdateMap)
    public void reportCurrentTime() {
        Lock lock = lockRegistry.obtain(lockKey);
        try {
            boolean success = lock.tryLock(lockTimeout, TimeUnit.SECONDS);
            int t = 0;
            for(; t< numberOfRetries && !success ; t++){
                success = lock.tryLock(lockTimeout, TimeUnit.SECONDS);
            }
            if(success) {
                updateTaskToDatabase();
            }
            else{
                log.info("lock not acquired tried " + t + " times");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    private static List<String> getKeysSortedByValues(Map<String, Integer> map) {
        // Create a list from the entries of the map
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());

        // Sort the list based on the values of the entries
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        // Create a new list to store the sorted keys
        List<String> sortedKeys = new ArrayList<>();

        // Add the sorted keys to the new list
        for (Map.Entry<String, Integer> entry : list) {
            sortedKeys.add(entry.getKey());
        }

        return sortedKeys;
    }



}
