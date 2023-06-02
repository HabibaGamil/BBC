package com.feed.feed.dbo.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class CategoryRequest implements Request{
    private String categoryId;

    @Override
    public String cacheParam() {
        return categoryId;
    }
}
