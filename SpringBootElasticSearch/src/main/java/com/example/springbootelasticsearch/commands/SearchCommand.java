package com.example.springbootelasticsearch.commands;

import com.example.springbootelasticsearch.entity.PostMetadataEntity;
import com.example.springbootelasticsearch.helper.Indices;
import com.example.springbootelasticsearch.search.SearchRequestDTO;
import com.example.springbootelasticsearch.service.PostsMetadataSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

@NoArgsConstructor
public class SearchCommand {
    private static final String POSTS_METADATA_INDEX = Indices.POSTS_METADATA_INDEX;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(SearchCommand.class);

    @Autowired
    private PostsMetadataSearchService postsMetadataService;


    public List<PostMetadataEntity> execute(SearchRequestDTO dto) throws IOException {
            return postsMetadataService.search(dto);
    }
}
