package com.example.views;


import com.example.views.RabbitMQ.Producer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.example.views.RabbitMQ.Producer;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class ViewService {
    @Autowired
    private  ViewsRepository viewsRepository;
    private Producer producer;
    public List<Views> getAllViews(){
        return viewsRepository.findAll();
    }

    public void createOrEditViews(String pID){
        String postID = pID;

        viewsRepository.findViewsByPostID(pID).ifPresentOrElse(v->{
            int currentCount=v.getView_count();
            v.setView_count(currentCount+1);
            viewsRepository.save(v);


        },()->{
            viewsRepository.insert(new Views(postID,1));
        });
    }
    public void deleteAViews(String postID){

        viewsRepository.findViewsByPostID(postID).ifPresentOrElse(v->{
           viewsRepository.deleteById(v.getId());

        },()->{
            throw new IllegalStateException();
        });
    }
    public Views getAViews(String postID){

        AtomicReference<Views> newViews = new AtomicReference<>(new Views());
        viewsRepository.findViewsByPostID(postID).ifPresentOrElse(v->{
             newViews.set(v);

            },()->{
            throw  new IllegalStateException();
        });

        return newViews.get();
    }
    public void deleteAllViews(){
        viewsRepository.deleteAll();
    }


    public void publishViews() {
        System.out.println("da5alt hena? ");
        producer.broadcastCurrentViewsCount();

    }

}
