package com.example.directory_app.api;

import com.example.directory_app.services.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/index")
public class IndexController {
    private final IndexService service;

    @Autowired
    public IndexController(IndexService indexService) {
            this.service= indexService;
    }

    @PostMapping("/recreate")
    public void recreateAlIndices(){
        service.recreateIndices(true);
    }
}
