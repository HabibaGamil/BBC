package com.generate.generate.repository.redis;


import com.generate.generate.model.redis.sub_category_data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//Yes, the repository that extends CRUD repository represents the table in Redis cache.
@Repository
public interface sub_categories_repository extends CrudRepository<sub_category_data,String> {
}
