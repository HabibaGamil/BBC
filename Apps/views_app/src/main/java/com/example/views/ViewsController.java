package com.example.views;

import com.example.views.RabbitMQ.Producer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/views")
@AllArgsConstructor
public class ViewsController {

    @Autowired
    private final ViewService viewService;
    private Producer producer;

    @GetMapping
    public List<Views> fetchAllViews() {
        return viewService.getAllViews();

    }

    record NewRequest(String postID) {
    }

    @PostMapping
    public void updateView(@RequestBody NewRequest request) {
        viewService.createOrEditViews(request.postID);

    }

    @GetMapping("/{postID}")
    public Views getAViews(@PathVariable("postID") String postId) {
        return viewService.getAViews(postId);

    }

    @DeleteMapping("/{postID}")
    public void deleteAViews(@PathVariable("postID") String postId) {
        viewService.deleteAViews(postId);

    }

    @Scheduled(fixedRate = 600000)
    @PostMapping("/publish_views")
    public void publishViews() {
        producer.broadcastCurrentViewsCount();
    }

}
