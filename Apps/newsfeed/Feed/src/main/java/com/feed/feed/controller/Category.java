package com.feed.feed.controller;

import com.feed.feed.commands.CategoryCommand;
import com.feed.feed.dbo.request.CategoryRequest;
import com.feed.feed.dbo.request.HomePageRequest;
import com.feed.feed.dbo.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Slf4j
public class Category {
    private final CategoryCommand categoryCommand;

    @GetMapping
    public Response getCategoryPage(@RequestBody CategoryRequest categoryRequest) {
        return categoryCommand.execute(categoryRequest);
    }
}
