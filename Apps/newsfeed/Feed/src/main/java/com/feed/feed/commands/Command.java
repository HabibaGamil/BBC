package com.feed.feed.commands;

import com.feed.feed.dbo.request.Request;
import com.feed.feed.dbo.response.Response;

public interface Command {

    public Response execute(Request request);


}
