package com.example.springbootelasticsearch.controller;

import com.example.springbootelasticsearch.entity.PostMetadataEntity;
import com.example.springbootelasticsearch.search.SearchRequestDTO;
import com.example.springbootelasticsearch.service.PostsMetadataSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/postMetadata")
public class PostsMetadataController {

    @Autowired
    private PostsMetadataSearchService postsMetadataSearchService;

    @Autowired
    public PostsMetadataController(PostsMetadataSearchService postsMetadataSearchService){
        this.postsMetadataSearchService= postsMetadataSearchService;
    }

    @GetMapping
    public Iterable<PostMetadataEntity> findAll(){
        return postsMetadataSearchService.findAll();
    }

    @GetMapping("/{id}")
    public PostMetadataEntity findById(@PathVariable  final String id){
        return postsMetadataSearchService.findById(id);
    }

    @PostMapping
    public void save(@RequestBody final PostMetadataEntity postsMetadataEntity){
        postsMetadataSearchService.index(postsMetadataEntity);
//        postsMetadataSearchService.save(postsMetadataEntity);
    }

    @PostMapping("/search")
    @CachePut(value = "searchTerms", key = "#dto.searchTerm + ' ' + #dto.fields", condition = "#result != null && !#result.isEmpty()")
    public List<PostMetadataEntity> search(@RequestBody final SearchRequestDTO dto) throws IOException {
        return postsMetadataSearchService.search(dto);
    }
}
