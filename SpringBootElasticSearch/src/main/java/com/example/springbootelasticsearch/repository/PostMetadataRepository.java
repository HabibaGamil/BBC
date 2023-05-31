package com.example.springbootelasticsearch.repository;

import com.example.springbootelasticsearch.entity.PostMetadataEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostMetadataRepository extends ElasticsearchRepository<PostMetadataEntity, String> {

}
