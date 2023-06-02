package com.example.springbootelasticsearch.service;

import com.example.springbootelasticsearch.helper.Indices;
import com.example.springbootelasticsearch.repository.PostMetadataRepository;
import com.example.springbootelasticsearch.search.SearchRequestDTO;
import com.example.springbootelasticsearch.search.util.SearchUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.springbootelasticsearch.entity.PostMetadataEntity;
//import com.example.springbootelasticsearch.repository.PostMetadataRepository;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PostsMetadataSearchServiceImp implements PostsMetadataSearchService {

    private static final String POSTS_METADATA_INDEX = Indices.POSTS_METADATA_INDEX;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(PostsMetadataSearchServiceImp.class);
    @Autowired
    private final PostMetadataRepository postMetadataRepository;
    private final RestHighLevelClient client;

    @Autowired
    public PostsMetadataSearchServiceImp(PostMetadataRepository postMetadataRepository, RestHighLevelClient client) {
        this.postMetadataRepository = postMetadataRepository;
        this.client = client;
    }


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


    // findById and getPostMetadataById are the same but implemented differently

//    public PostMetadataEntity findById(final long id) {
//        return postMetadataRepository.findById(id).orElse(null);
//    }


    public PostMetadataEntity findById(final String id) {
        return postMetadataRepository.findById(id).orElse(null);
    }

    public PostMetadataEntity getPostMetadataById(String id) throws IOException, IOException {
        try {
            GetRequest getRequest = new GetRequest(POSTS_METADATA_INDEX, id);
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            return MAPPER.readValue(getResponse.getSourceAsString(), PostMetadataEntity.class);
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }
    public Iterable<PostMetadataEntity> findAll() {
        return postMetadataRepository.findAll();
    }

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

}