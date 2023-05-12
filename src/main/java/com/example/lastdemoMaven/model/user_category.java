//package com.example.lastdemoMaven.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.data.cassandra.core.mapping.CassandraType;
//import org.springframework.data.cassandra.core.mapping.PrimaryKey;
//import org.springframework.data.cassandra.core.mapping.Table;
//
//import java.util.Map;
//
////import jakarta.persistence.*;
//
//
//@Table
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class user_category {
//
//    @PrimaryKey
//    private int userId;
//
//    @CassandraType(type = CassandraType.Name.MAP, typeArguments = {CassandraType.Name.INT, CassandraType.Name.INT})
//    private Map<Integer, Integer> categoryCount;
//
//
//    @Override
//    public String toString() {
//        return "user_category{" +
//                "userId=" + userId +
//                ", categoryCount=" + categoryCount +
//                '}';
//    }
//}
