package com.example.directory_app.services;

import com.example.directory_app.entities.CategoryEntity;
import com.example.directory_app.entities.SubcategoryEntity;
import com.example.directory_app.entities.TopicEntity;
import com.example.directory_app.repositories.CategoryRepository;
import com.example.directory_app.repositories.SubcategoryRepository;
import com.example.directory_app.repositories.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DirectoryService {


private final CategoryRepository categoryRepository;
private final SubcategoryRepository subcategoryRepository;
private final TopicRepository topicRepository;


//get all catgeories + findCategoryById
public Iterable<CategoryEntity> findAllCategories() {
    return categoryRepository.findAll();
}

public CategoryEntity findCategoryById(final String id) {
        return categoryRepository.findById(id).orElse(null);
    }

//get all subcategories + findSubcategoryById
public Iterable<SubcategoryEntity> findAllSubcategories() {
    return subcategoryRepository.findAll();
}

public SubcategoryEntity findSubcategoryById(final String id) {
        return subcategoryRepository.findById(id).orElse(null);
}

//get all topics   + findTopicById
public Iterable<TopicEntity> findAllTopics() {
    return topicRepository.findAll();
}

public TopicEntity findTopicById(final String id) {
        return topicRepository.findById(id).orElse(null);
    }




}
