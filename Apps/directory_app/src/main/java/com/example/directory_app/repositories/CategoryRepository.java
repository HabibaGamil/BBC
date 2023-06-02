package com.example.directory_app.repositories;

import com.example.directory_app.entities.CategoryEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CategoryRepository extends ElasticsearchRepository<CategoryEntity, String> {
}
