package com.example.directory_app.repositories;

import com.example.directory_app.entities.PostMetadataEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostMetadataRepository extends ElasticsearchRepository<PostMetadataEntity, String> {
}
