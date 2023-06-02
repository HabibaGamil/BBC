package com.generate.generate.model.cassandra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

//import jakarta.persistence.*;


@Table(value = "user_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCategory implements Serializable {

    @PrimaryKey
    private String userId;

    @CassandraType(type = CassandraType.Name.MAP, typeArguments = {CassandraType.Name.INT, CassandraType.Name.INT})
    private Map<String, Integer> categoryCount;


    @Override
    public String toString() {
        return "user_category{" +
                "userId=" + userId +
                ", categoryCount=" + categoryCount +
                '}';
    }
}
