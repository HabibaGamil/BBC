package com.example.views;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface ViewsRepository extends MongoRepository<Views, String>{
Optional<Views> findViewsByPostID (String postID);


}
