package com.example.directory_app.services;

import com.example.directory_app.entities.PostMetadataEntity;
import com.example.directory_app.helper.Indices;
import com.example.directory_app.repositories.PostMetadataRepository;
import com.example.directory_app.search.SearchRequestDTO;
import com.example.directory_app.search.util.SearchUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class PostMetadataService {

    private static final String POSTS_METADATA_INDEX = Indices.POSTS_METADATA_INDEX;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(PostMetadataService.class);
    @Autowired
    private final PostMetadataRepository postMetadataRepository;
    private final RestHighLevelClient client;

    // save postmetadata
    //save and index are the same but implemented differently
    public Boolean index(final PostMetadataEntity postsMetadataEntity) {
        try {
            final String postMetadataAsString = MAPPER.writeValueAsString(postsMetadataEntity);

            final IndexRequest request = new IndexRequest(POSTS_METADATA_INDEX);
            request.id(postsMetadataEntity.getPostMetadataId() + "");
            request.source(postMetadataAsString, XContentType.JSON);

            final IndexResponse response = client.index(request, RequestOptions.DEFAULT);

            return response != null && response.status().equals(RestStatus.OK);

        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    public void save(final PostMetadataEntity postsMetadataEntity) {
        postMetadataRepository.save(postsMetadataEntity);
    }

    //    delete postmetadata
    public void deleteById(final String id) {
        postMetadataRepository.deleteById(id);
    }

//    get postmetadata that belong to the same categoryId/subcategoryId/topic
//    + include pagination( restrict number of posts retrieved + should be sorted)
    public List<PostMetadataEntity> search(final SearchRequestDTO dto) throws IOException {
        final SearchRequest request = SearchUtil.buildSearchRequest(POSTS_METADATA_INDEX, dto);


        if(request == null){
            LOG.error("Failed to build search request");
            return Collections.emptyList();
        }
        try{
            final SearchResponse response = client.search(request, RequestOptions.DEFAULT);

            final SearchHit[] searchHits= response.getHits().getHits();
            final List<PostMetadataEntity> postsMetadataEntities
                    = new ArrayList<>(searchHits.length);
            for(SearchHit hit : searchHits){
                postsMetadataEntities.add(MAPPER.readValue(hit.getSourceAsString(), PostMetadataEntity.class));
            }
            return postsMetadataEntities;
        } catch (Exception e){
            LOG.error(e.getMessage(),e);
            return Collections.emptyList();
        }
    }

    public Iterable<PostMetadataEntity> findAll() {
        return postMetadataRepository.findAll();
    }


    public PostMetadataEntity findById(final String id) {
        return postMetadataRepository.findById(id).orElse(null);
    }
}
