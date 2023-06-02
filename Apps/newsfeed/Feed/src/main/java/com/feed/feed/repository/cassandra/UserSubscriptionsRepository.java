package com.feed.feed.repository.cassandra;


import com.feed.feed.model.cassandra.UserSubscriptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



public interface UserSubscriptionsRepository extends CassandraRepository<UserSubscriptions, String> {
}
