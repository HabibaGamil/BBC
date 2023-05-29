package com.example.views;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/views")
@AllArgsConstructor
public class ViewsController {
    private final ViewService viewService;

    @GetMapping
    public List<Views> fetchAllViews(){
        return viewService.getAllViews();

    }

    record NewRequest(String postID){}
    @PostMapping
    public void updateView(@RequestBody NewRequest request){
        viewService.createOrEditViews(request.postID);

    }
    @GetMapping("{postID}")
    public Views getAViews(@PathVariable("postID") String postId ){
        return viewService.getAViews(postId);

    }
    @DeleteMapping("{postID}")
    public void deleteAViews(@PathVariable("postID") String postId ){
        viewService.deleteAViews(postId);

    }

}
