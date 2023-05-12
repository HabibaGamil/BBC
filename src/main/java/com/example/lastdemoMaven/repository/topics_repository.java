package com.example.lastdemoMaven.repository;


import com.example.lastdemoMaven.model.topic_data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//Yes, the repository that extends CRUD repository represents the table in Redis cache.
@Repository
public interface topics_repository extends CrudRepository<topic_data,String> {
}
