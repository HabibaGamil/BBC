package com.example.post_mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface postRepository extends MongoRepository<Article,String> {

}
