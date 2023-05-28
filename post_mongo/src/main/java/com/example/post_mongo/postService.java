package com.example.post_mongo;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class postService {
    @Autowired
    private final postRepository postRepository;
    @Autowired
    private final mediaRepository mediaRepository;

    public List<Article> getAllArticles(){
        return postRepository.findAll();
    }

    public Optional<Article> getArticle(String id){
        return postRepository.findById(id);
    }

    public List<MediaServer> getAllMedia(){
        return mediaRepository.findAll();
    }

    public Article addArticles(Article post){
        return postRepository.save(post);
    }

    public MediaServer addMedia(MediaServer media){
        return mediaRepository.save(media);
    }
}
