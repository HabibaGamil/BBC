package com.feed.feed.dbo.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class SubCategoryRequest implements Request{
    private String subCategoryId;

    @Override
    public String cacheParam() {
        return subCategoryId;
    }
}
