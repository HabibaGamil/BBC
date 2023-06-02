package com.generate.generate.repository.cassandra;


import com.generate.generate.model.cassandra.UserPreferences;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserPreferencesRepository extends CassandraRepository<UserPreferences, String> {
}
