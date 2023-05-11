package com.example.directory_app.repositories;
import com.example.directory_app.entities.SubcategoryEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SubcategoryRepository extends ElasticsearchRepository<SubcategoryEntity, String> {
}
