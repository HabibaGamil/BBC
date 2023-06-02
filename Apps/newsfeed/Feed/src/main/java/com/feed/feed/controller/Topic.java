package com.feed.feed.controller;

import com.feed.feed.commands.CategoryCommand;
import com.feed.feed.commands.TopicCommand;
import com.feed.feed.dbo.request.CategoryRequest;
import com.feed.feed.dbo.request.TopicRequest;
import com.feed.feed.dbo.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
@Slf4j
public class Topic {
    private final TopicCommand topicCommand;

    @GetMapping
    public Response getTopicPage(@RequestBody TopicRequest topicRequest) {
        return topicCommand.execute(topicRequest);
    }
}
