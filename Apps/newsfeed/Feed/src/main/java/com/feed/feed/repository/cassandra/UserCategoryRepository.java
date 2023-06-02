package com.feed.feed.repository.cassandra;


import com.feed.feed.model.cassandra.UserCategory;
import org.springframework.data.cassandra.repository.CassandraRepository;


public interface UserCategoryRepository extends CassandraRepository<UserCategory, String> {
}
