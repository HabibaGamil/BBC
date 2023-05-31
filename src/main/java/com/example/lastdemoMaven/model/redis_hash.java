//package com.example.lastdemoMaven.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.redis.core.RedisHash;
//
//import java.io.Serializable;
//import java.util.HashMap;
//
//// redisHash represents row in redis table whose key we will agree on
//// this row will only contains hashmap which will hold each post meta data that need to be posted immediately
//
//@RedisHash
//@Getter
//@Setter
//public class redis_hash implements Serializable {
//
//    @Id
//    private final String key;
//
//    private HashMap<String,post_data> hashMap = new HashMap<>();
//
//    public redis_hash(String key){
//        this.key = key;
//    }
//
//    public HashMap<String, post_data> getHashMap() {
//        return hashMap;
//    }
//
//    public void setHashMap(HashMap<String, post_data> hashMap) {
//        this.hashMap = hashMap;
//    }
//
//
//}
