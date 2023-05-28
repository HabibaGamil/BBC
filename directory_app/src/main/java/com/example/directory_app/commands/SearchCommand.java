package com.example.directory_app.commands;

import com.example.directory_app.entities.PostMetadataEntity;
import com.example.directory_app.helper.Indices;
import com.example.directory_app.repositories.PostMetadataRepository;
import com.example.directory_app.search.SearchRequestDTO;
import com.example.directory_app.services.PostMetadataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

@NoArgsConstructor
public class SearchCommand {
    private static final String POSTS_METADATA_INDEX = Indices.POSTS_METADATA_INDEX;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(PostMetadataService.class);

    @Autowired
    private PostMetadataService postsMetadataService;


    public List<PostMetadataEntity> execute(SearchRequestDTO dto) throws IOException {
            return postsMetadataService.search(dto);
    }
}
