package com.generate.generate.repository.cassandra;


import com.generate.generate.model.cassandra.UserCategory;
import org.springframework.data.cassandra.repository.CassandraRepository;


public interface UserCategoryRepository extends CassandraRepository<UserCategory, String> {
}
