package com.example.springbootelasticsearch.service;

import com.example.springbootelasticsearch.entity.PostMetadataEntity;
import com.example.springbootelasticsearch.search.SearchRequestDTO;

import java.io.IOException;
import java.util.List;

public interface PostsMetadataSearchService {
    public List<PostMetadataEntity> search(final SearchRequestDTO dto) throws IOException;
    public Boolean index(final PostMetadataEntity postsMetadataEntity);
    public PostMetadataEntity getPostMetadataById(String id) throws IOException, IOException;
    public void save(final PostMetadataEntity postsMetadataEntity);
    public PostMetadataEntity findById(final String id);
    public Iterable<PostMetadataEntity> findAll();


    }
