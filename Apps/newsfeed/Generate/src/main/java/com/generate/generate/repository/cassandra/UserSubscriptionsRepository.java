package com.generate.generate.repository.cassandra;


import com.generate.generate.model.cassandra.UserSubscriptions;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserSubscriptionsRepository extends CassandraRepository<UserSubscriptions, String> {
}
