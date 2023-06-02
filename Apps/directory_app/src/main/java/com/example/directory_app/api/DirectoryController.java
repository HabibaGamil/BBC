package com.example.directory_app.api;

import com.example.directory_app.entities.CategoryEntity;
import com.example.directory_app.entities.SubcategoryEntity;
import com.example.directory_app.entities.TopicEntity;
import com.example.directory_app.services.DirectoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/directory")
@AllArgsConstructor
public class DirectoryController {
    @Autowired
    private DirectoryService directoryService;

    @GetMapping("/categories")
    public Iterable<CategoryEntity> findAllCategories() {
        return directoryService.findAllCategories();
    }

    @GetMapping("/subcategories")
    public Iterable<SubcategoryEntity> findAllSubCategories(){
        return directoryService.findAllSubcategories();
    }

    @GetMapping("/topics")
    public Iterable<TopicEntity> findAllTopics(){
        return directoryService.findAllTopics();
    }

    @GetMapping("/category/{id}")
    public CategoryEntity findByCategoryId(@PathVariable final String id){
        return directoryService.findCategoryById(id);
    }
    @GetMapping("/subcategory/{id}")
    public SubcategoryEntity findSubcategoryById(@PathVariable final String id){
        return directoryService.findSubcategoryById(id);
    }
    @GetMapping("/topic/{id}")
    public TopicEntity findTopicById(@PathVariable final String id){
        return directoryService.findTopicById(id);
    }

    @GetMapping("/subcategoriesOfCategory/{id}")
    public List<SubcategoryEntity> findAllSubcategoriesBelongingtoCategory(@PathVariable final String id){
        List<SubcategoryEntity> temp =  StreamSupport.stream(directoryService.findAllSubcategories().spliterator(), false).collect(Collectors.toList());
        temp.removeIf(p -> !p.getFk_categoryId().equals(id));
        return temp;
    }
    @GetMapping("/topicsOfSubcategory/{id}")
    public List<TopicEntity> findAllTopicsBelongingtoSubCategory(@PathVariable final String id){
        List<TopicEntity>  temp =  StreamSupport.stream(directoryService.findAllTopics().spliterator(), false).collect(Collectors.toList());
        temp.removeIf(p -> !p.getFk_subCategoryId().equals(id));
        return temp;
    }

    @GetMapping("/relatedTopics/{id}")
    public List<String> findAllRelatedTopics(@PathVariable final String id){
        List<String> temp = directoryService.findTopicById(id).getRelatedTopics();
        return temp;
    }

}
