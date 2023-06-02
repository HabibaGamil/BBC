package com.generate.generate.service.cassandra;



import com.generate.generate.dbo.cassandra.SubscriptionRequest;
import com.generate.generate.model.cassandra.UserSubscriptions;
import com.generate.generate.repository.cassandra.UserSubscriptionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {
    private final UserSubscriptionsRepository userSubscriptionsRepository;

//    private final ExpirableLockRegistry lockRegistry;
//
//    private static final String lockKey = "subscribeTaskToDatabase";
//
//    private static final long lockTimeout = 10000L;
//
//    private static final int numberOfRetries = 10;
//
//    private static final int timeToUpdateMap = 15000;


    private UserSubscriptions getUserSubscriptions(String userId){
        Optional<UserSubscriptions> userSubscriptions = userSubscriptionsRepository.findById(userId);
        if(userSubscriptions.isPresent()){
            return userSubscriptions.get();
        }
        else{
            return new UserSubscriptions(userId, new ArrayList<>(),new ArrayList<>());
        }
    }

    public void subscribe(SubscriptionRequest subscriptionRequest){
        UserSubscriptions userSubscriptions = getUserSubscriptions(subscriptionRequest.getUserId());
        userSubscriptions.setTopicsId(subscriptionRequest.getTopicsId());
        userSubscriptions.setTopicsName(subscriptionRequest.getTopicsName());
        userSubscriptionsRepository.save(userSubscriptions);

    }

//    public void subscribe(SubscriptionRequest subscriptionRequest) throws RuntimeException{
//
//        Lock lock = lockRegistry.obtain(lockKey + subscriptionRequest.getUserId());
//        try {
//            boolean success = lock.tryLock(lockTimeout, TimeUnit.SECONDS);
//            int t = 0;
//            for(; t< numberOfRetries && !success ; t++){
//                success = lock.tryLock(lockTimeout, TimeUnit.SECONDS);
//            }
//            if(success) {
//                taskSubscribe(subscriptionRequest);
//            }
//            else{
//                log.info("lock not acquired tried subscribe" + t + " times");
//                throw new RuntimeException("this key busy");
//            }
//        }
//        catch (Exception e){
//            throw new RuntimeException();
//
//        }
//        finally {
//            lock.unlock();
//        }
//
//    }


}
