//package com.generate.generate.service;
//
//
//import com.generate.generate.dbo.ViewRequest;
//import com.generate.generate.model.user_category;
//import com.generate.generate.repository.user_categoryRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.integration.support.locks.ExpirableLockRegistry;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.Lock;
//
//
//@Service
//@RequiredArgsConstructor
//@Slf4j


//public class ViewService {
//
//    private final HashMap<Integer,HashMap<Integer,Integer>> categoryMap;
//    private final user_categoryRepository userCategoryRepository;
//
//
//    private final ExpirableLockRegistry lockRegistry;
//
//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//
//
//
//    private static final String lockKey = "updateTaskToDatabase";
//
//    private static final long lockTimeout = 1000L;
//
//    private static final int numberOfRetries = 10;
//
//    private static final int timeToUpdateMap = 15000;
//
//
//    public void upadteViewCount(ViewRequest viewRequest){
//        if(categoryMap.containsKey(viewRequest.getUserId())){
//            if(categoryMap.get(viewRequest.getUserId()).containsKey(viewRequest.getCategoryId())){
//                categoryMap.get(viewRequest.getUserId()).put(viewRequest.getCategoryId(),categoryMap.get(viewRequest.getUserId()).get(viewRequest.getCategoryId())+1);
//            }
//            else{
//                categoryMap.get(viewRequest.getUserId()).put(viewRequest.getCategoryId(), 1);
//            }
//
//        }
//        else {
//            HashMap<Integer,Integer> categoryCount = new HashMap<>();
//            categoryCount.put(viewRequest.getCategoryId(), 1);
//            categoryMap.put(viewRequest.getUserId(),categoryCount);
//
//        }
//
//    }
//
//
//    private List<user_category> getUserCategory(){
//        List<Integer> ids = categoryMap.keySet().stream().toList();
//        Set<Integer> setIds = new HashSet<>();
//        List<user_category> user_category_data = userCategoryRepository.findAllById(ids);
//        for(user_category userCategory : user_category_data){
//            setIds.add(userCategory.getUserId());
//        }
//        for(int id : ids){
//            if(!setIds.contains(id)){
//                user_category userCategory = new user_category();
//                userCategory.setUserId(id);
//                Map<Integer,Integer> categoryCount = new HashMap<>();
//                userCategory.setCategoryCount(categoryCount);
//                user_category_data.add(userCategory);
//            }
//
//        }
//        return user_category_data;
//    }
//
//
//
//    private void updateTaskToDatabase(){
//        List<user_category> userCategoryList = getUserCategory();
//        for(user_category userCategory : userCategoryList){
//            int userId = userCategory.getUserId();
//            for(int categoryId : categoryMap.get(userId).keySet()){
//                if(userCategory.getCategoryCount().containsKey(categoryId)){
//                    userCategory.getCategoryCount().put(categoryId,userCategory.getCategoryCount().get(categoryId)+categoryMap.get(userId).get(categoryId));
//                }
//                else{
//                    userCategory.getCategoryCount().put(categoryId, categoryMap.get(userId).get(categoryId));
//                }
//            }
//        }
//        categoryMap.clear();
//
//        userCategoryRepository.saveAll(userCategoryList);
//
//    }
//    @Scheduled(fixedRate = timeToUpdateMap)
//    public void reportCurrentTime() {
//        Lock lock = lockRegistry.obtain(lockKey);
//        try {
//            boolean success = lock.tryLock(lockTimeout, TimeUnit.SECONDS);
//            int t = 0;
//            for(; t< numberOfRetries && !success ; t++){
//                success = lock.tryLock(lockTimeout, TimeUnit.SECONDS);
//            }
//            if(success) {
//                updateTaskToDatabase();
//            }
//            else{
//                log.info("lock not acquired tried " + t + " times");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            lock.unlock();
//        }
//
//    }
//
//}
