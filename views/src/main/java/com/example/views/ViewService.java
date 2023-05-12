package com.example.views;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

@AllArgsConstructor
@Service
public class ViewService {
    private final ViewsRepository viewsRepository;
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

}
