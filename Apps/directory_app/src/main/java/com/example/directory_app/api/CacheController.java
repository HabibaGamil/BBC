package com.example.directory_app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private CacheManager cacheManager;

    @GetMapping("/searchTerms")
    public Object getSearchTermsCache() {
        Cache searchTermsCache = cacheManager.getCache("searchTerms");
        return searchTermsCache != null ? searchTermsCache.getNativeCache() : null;
    }
}
