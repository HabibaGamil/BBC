package com.feed.feed.repository.cassandra;


import com.feed.feed.model.cassandra.UserPreferences;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserPreferencesRepository extends CassandraRepository<UserPreferences, String> {
}
