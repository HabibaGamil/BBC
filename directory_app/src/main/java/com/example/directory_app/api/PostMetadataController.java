package com.example.directory_app.api;

import com.example.directory_app.DataClasses.SearchRequest;
import com.example.directory_app.DataClasses.SearchResponse;
import com.example.directory_app.RabbitMQ.Producer;
import com.example.directory_app.config.Config;
import com.example.directory_app.config.Properties;
import com.example.directory_app.entities.PostMetadataEntity;
import com.example.directory_app.repositories.PostMetadataRepository;
import com.example.directory_app.search.SearchRequestDTO;
import com.example.directory_app.services.PostMetadataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    Config config;
    private Producer producer;
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

    @GetMapping("/app/properties")
    public String getPropertyDetails() throws JsonProcessingException, ClassNotFoundException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(config.getMsg(),config.getCmdMap(),config.getModifiableClasses());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }

    @PostMapping("/search")
    @CachePut(value = "searchTerms", key = "#dto.searchTerm + ' ' + #dto.fields")
    public List<PostMetadataEntity> search(@RequestBody final SearchRequestDTO dto) throws IOException {
        return postsMetadataService.search(dto);
    }

    @PostMapping("/search/queue")
//    @CachePut(value = "searchTerms", key = "#dto.searchTerm + ' ' + #dto.fields")
    public SearchResponse search_queue(@RequestBody final SearchRequest req) throws IOException {
        return producer.sendMessage(req);
    }
}
