package com.example.lastdemoMaven.service;

import com.example.lastdemoMaven.dbo.SearchResponse;
import com.example.lastdemoMaven.model.category_data;
import com.example.lastdemoMaven.model.meta_data;
import com.example.lastdemoMaven.model.sub_category_data;
import com.example.lastdemoMaven.model.topic_data;
import com.example.lastdemoMaven.repository.categories_repository;
import com.example.lastdemoMaven.repository.sub_categories_repository;
import com.example.lastdemoMaven.repository.topics_repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {


    private final categories_repository CR;
    private final sub_categories_repository SCR;
    private final topics_repository TD;


    public void insertDataToCategoryCache(String category_name, String category_id, meta_data [] most_viewed, meta_data [] most_recent) {
        category_data newData = new category_data(category_id,category_name,most_viewed,most_recent);
        CR.save(newData);
    }

    public void insertDataToSubCategoryCache(String sub_category_name, String sub_category_id, meta_data [] most_viewed,meta_data [] most_recent) {
        sub_category_data newData = new sub_category_data(sub_category_id,sub_category_name,most_viewed,most_recent);
        SCR.save(newData);
    }

    public void insertDataToTopicCache(String topic_name, String topic_id, meta_data [] most_viewed,meta_data [] most_recent) {
        topic_data newData = new topic_data(topic_id,topic_name,most_viewed,most_recent);
        TD.save(newData);
    }

    public category_data getByIdFromCategoryCache(String id) {
        return CR.findById(id).orElse(null);
    }

    public sub_category_data getByIdFromSubCategoryCache(String id) {
        return SCR.findById(id).orElse(null);
    }

    public topic_data getByIdFromTopicsCache(String id) {
        return TD.findById(id).orElse(null);
    }

    public void update_cache(SearchResponse response){
       List<meta_data[]> meta_data= response.getMetadata();
       String [] ids = response.getIds();
        switch (response.getType()) {
            case 0 -> {
                for (int i = 0; i < meta_data.size(); i++) {
                    meta_data[] meta_data_per_category = meta_data.get(i);
                    meta_data metaData = meta_data_per_category[0];
                    String category_name = metaData.getName();
                    //String[] category_ids = metaData.getIds();
                    String category_id = ids[i];
                    //category_data specificRow = getByIdFromCategoryCache(category_name);
                    category_data specificRow = getByIdFromCategoryCache(category_id);
                    if (specificRow == null) {
                        addNewRow(response.isMost_viewed(), 0, category_name, category_id, meta_data_per_category);

                    }
                    else {
                        updateExistedRow(specificRow, response.isMost_viewed(), meta_data_per_category, 0);
                    }
                }
            }
            case 1 -> {
                for (int i = 0; i < meta_data.size(); i++) {
                    meta_data[] meta_data_per_sub_category = meta_data.get(i);
                    meta_data metaData = meta_data_per_sub_category[0];
                    String sub_category_name =  metaData.getName();
                    //String[] sub_category_ids = metaData.getIds();
                    String sub_category_id = ids[i];
                    //category_data specificRow = getByIdFromCategoryCache(sub_category_name);
                    category_data specificRow = getByIdFromCategoryCache(sub_category_id);
                    if (specificRow == null)
                        addNewRow(response.isMost_viewed(), 1, sub_category_name, sub_category_id, meta_data_per_sub_category);
                    else {
                        updateExistedRow(specificRow, response.isMost_viewed(),  meta_data_per_sub_category, 1);
                    }
                }
            }
            case 2 ->{
                for (int i = 0; i < meta_data.size(); i++) {
                    meta_data[] meta_data_per_topic = meta_data.get(i);
                    meta_data metaData = meta_data_per_topic[0];
                    String topic_name =  metaData.getName();
                    //String[] topic_ids =  metaData.getIds();
                    String topic_id =  ids[i];
                    //category_data specificRow = getByIdFromCategoryCache(topic_name);
                    category_data specificRow = getByIdFromCategoryCache(topic_id);
                    if (specificRow == null)
                        addNewRow(response.isMost_viewed(), 2, topic_name, topic_id, meta_data_per_topic);
                    else {
                        updateExistedRow(specificRow, response.isMost_viewed(), meta_data_per_topic, 2);
                    }
                }
            }
        }
    }

    private void addNewRow(boolean most_view_Or_recent, int type,String name,String id,meta_data [] meta_data_per_category){
        if(most_view_Or_recent){
            switch (type) {
                case 0 -> insertDataToCategoryCache(name, id, meta_data_per_category, null);
                case 1 -> insertDataToSubCategoryCache(name, id, meta_data_per_category, null);
                case 2 -> insertDataToTopicCache(name, id, meta_data_per_category, null);
            }
        }
        else{
            switch (type) {
                case 0 -> insertDataToCategoryCache(name, id, null, meta_data_per_category);
                case 1 -> insertDataToSubCategoryCache(name, id, null, meta_data_per_category);
                case 2 -> insertDataToTopicCache(name, id, null, meta_data_per_category);
            }
        }
    }

    private void updateExistedRow(Object rowToBeUpdated, boolean whichField, meta_data [] updatedValue, int type){
        switch (type) {
            case 0 -> {
                assert rowToBeUpdated instanceof category_data;
                category_data temp = (category_data) rowToBeUpdated;
                if (whichField)
                    temp.setMost_viewed(updatedValue);
                else
                    temp.setMost_recent(updatedValue);
                CR.save(temp);
            }
            case 1 -> {
                assert rowToBeUpdated instanceof sub_category_data;
                sub_category_data temp = (sub_category_data) rowToBeUpdated;
                if (whichField)
                    temp.setMost_viewed(updatedValue);
                else
                    temp.setMost_recent(updatedValue);
                SCR.save(temp);
            }
            case 2 -> {
                assert rowToBeUpdated instanceof topic_data;
                topic_data temp = (topic_data) rowToBeUpdated;
                if (whichField)
                    temp.setMost_viewed(updatedValue);
                else
                    temp.setMost_recent(updatedValue);
                TD.save(temp);
            }
        }
    }

}



