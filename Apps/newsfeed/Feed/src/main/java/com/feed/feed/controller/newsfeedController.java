package com.feed.feed.controller;

import com.feed.feed.commands.Command;
import com.feed.feed.commands.CommandFactory;
import com.feed.feed.dbo.request.Request;
import com.feed.feed.dbo.request.RequestFactory;
import com.feed.feed.dbo.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/bbc/newsfeed")
@RequiredArgsConstructor
@Slf4j
public class newsfeedController {
    private final CommandFactory commandFactory;
    private final RequestFactory requestFactory;

    @GetMapping("/{action}")
    public Response newsfeed(@PathVariable String action, @RequestBody Map<String, Object> requestBody){
        Request request = requestFactory.getRequest(action,requestBody);
        Command command = commandFactory.getCommand(action);
        Response response = command.execute(request);
        return response;
    }



}
