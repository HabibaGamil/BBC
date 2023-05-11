package com.example.directory_app.api;

import com.example.directory_app.entities.PostMetadataEntity;
import com.example.directory_app.repositories.PostMetadataRepository;
import com.example.directory_app.search.SearchRequestDTO;
import com.example.directory_app.services.PostMetadataService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/postMetadata")
@AllArgsConstructor
public class PostMetadataController {
    @Autowired
    private PostMetadataService postsMetadataService;

    @GetMapping
    public Iterable<PostMetadataEntity> findAll(){
        return postsMetadataService.findAll();
    }

    @GetMapping("/{id}")
    public PostMetadataEntity findById(@PathVariable final String id){
        return postsMetadataService.findById(id);
    }

    @PostMapping
    public void save(@RequestBody final PostMetadataEntity postsMetadataEntity){
        postsMetadataService.index(postsMetadataEntity);
//        postsMetadataSearchService.save(postsMetadataEntity);
    }

    @PostMapping("/search")
    @CachePut(value = "searchTerms", key = "#dto.searchTerm + ' ' + #dto.fields")
    public List<PostMetadataEntity> search(@RequestBody final SearchRequestDTO dto) throws IOException {
        return postsMetadataService.search(dto);
    }
}
