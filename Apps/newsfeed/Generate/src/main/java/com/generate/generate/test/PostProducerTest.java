package com.generate.generate.test;

import com.generate.generate.dbo.redis.PostEvent;
import com.generate.generate.model.redis.PostMetadataEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostProducerTest {

    private final RabbitTemplate rabbitTemplate;

    public PostEvent publishPost(){
        PostEvent postEvent = getPost();
        rabbitTemplate.convertAndSend("newsfeed_post_queue", postEvent);
        return postEvent;
    }
    private PostEvent getPost(){
        PostMetadataEntity postMetadataEntity = new PostMetadataEntity();
        int ran = (int)(Math.random()*100);
        postMetadataEntity.setPostMetadataId(String.valueOf(ran));
        char c = (char)('a'+ran%26);
        String title = String.valueOf(c)+c+c;
        String summary = String.valueOf(c)+c+c+c;
        postMetadataEntity.setTitle(title);
        postMetadataEntity.setSummary(summary);
        return new PostEvent(postMetadataEntity);

    }
}
