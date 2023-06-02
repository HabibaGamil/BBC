package com.generate.generate.repository.redis;


import com.generate.generate.model.redis.category_data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//Yes, the repository that extends CRUD repository represents the table in Redis cache.
@Repository
public interface categories_repository extends CrudRepository<category_data,String> {

}
