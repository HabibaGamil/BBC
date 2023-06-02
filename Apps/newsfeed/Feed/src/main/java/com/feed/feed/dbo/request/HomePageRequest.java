package com.feed.feed.dbo.request;


import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class HomePageRequest implements Request{
    private String userId;

    @Override
    public String cacheParam() {
        return userId;
    }
}
