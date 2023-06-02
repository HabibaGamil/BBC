package com.example.directory_app.repositories;
import com.example.directory_app.entities.TopicEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TopicRepository extends ElasticsearchRepository<TopicEntity, String> {
}
