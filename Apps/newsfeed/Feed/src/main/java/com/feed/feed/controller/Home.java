package com.feed.feed.controller;


import com.feed.feed.commands.Command;
import com.feed.feed.commands.HomePageCommand;
import com.feed.feed.dbo.request.HomePageRequest;
import com.feed.feed.dbo.request.Request;
import com.feed.feed.dbo.response.HomePageResponse;
import com.feed.feed.dbo.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
@Slf4j
public class Home {
    private final HomePageCommand homePageCommand;


    @GetMapping
    public Response getHomePage(@RequestBody HomePageRequest homePageRequest) {
        return homePageCommand.execute(homePageRequest);
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
