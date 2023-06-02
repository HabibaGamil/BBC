package com.generate.generate.service.redis;


import com.generate.generate.dbo.redis.SearchResponse;
import com.generate.generate.model.redis.category_data;
import com.generate.generate.model.redis.PostMetadataEntity;
import com.generate.generate.model.redis.sub_category_data;
import com.generate.generate.model.redis.topic_data;
import com.generate.generate.repository.redis.categories_repository;
import com.generate.generate.repository.redis.sub_categories_repository;
import com.generate.generate.repository.redis.topics_repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {


    private final categories_repository CR;
    private final sub_categories_repository SCR;
    private final topics_repository TD;


    public void insertDataToCategoryCache(String category_id, List<PostMetadataEntity> most_viewed, List<PostMetadataEntity> most_recent) {
        category_data newData = new category_data(category_id,most_viewed,most_recent);
        CR.save(newData);
        System.out.println("updated");
    }

    public void insertDataToSubCategoryCache(String sub_category_id, List<PostMetadataEntity> most_viewed, List<PostMetadataEntity> most_recent) {
        sub_category_data newData = new sub_category_data(sub_category_id,most_viewed,most_recent);
        SCR.save(newData);
        System.out.println("updated");
    }

    public void insertDataToTopicCache(String topic_id, List<PostMetadataEntity> most_viewed, List<PostMetadataEntity> most_recent) {
        topic_data newData = new topic_data(topic_id,most_viewed,most_recent);
        TD.save(newData);
        System.out.println("updated");
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
        List<List<PostMetadataEntity>> meta_data= response.getMetadata();
        meta_data = validating_number_of_posts(meta_data,response.getNumber_of_posts());
        List<String>ids = response.getIds();
        switch (response.getType()) {
            case 0 -> {
                for (int i = 0; i < meta_data.size(); i++) {
                    List<PostMetadataEntity> meta_data_per_category = meta_data.get(i);
                    //PostMetadataEntity metaData = meta_data_per_category.get(0);
                    //String category_name = metaData.getName();
                    String category_id = ids.get(i);
                    category_data specificRow = getByIdFromCategoryCache(category_id);
                    if (specificRow == null) {
                        addNewRow(response.isMost_viewed(), 0, category_id, meta_data_per_category);
                    }
                    else {
                        updateExistedRow(specificRow, response.isMost_viewed(), meta_data_per_category, 0);
                    }
                }
            }
            case 1 -> {
                for (int i = 0; i < meta_data.size(); i++) {
                    List<PostMetadataEntity> meta_data_per_sub_category = meta_data.get(i);
                    //PostMetadataEntity metaData = meta_data_per_sub_category.get(0);
                    //String sub_category_name =  metaData.getName();
                    String sub_category_id = ids.get(i);
                    sub_category_data specificRow = getByIdFromSubCategoryCache(sub_category_id);
                    if (specificRow == null)
                        addNewRow(response.isMost_viewed(), 1, sub_category_id, meta_data_per_sub_category);
                    else {
                        updateExistedRow(specificRow, response.isMost_viewed(),  meta_data_per_sub_category, 1);
                    }
                }
            }
            case 2 ->{
                for (int i = 0; i < meta_data.size(); i++) {
                    List<PostMetadataEntity> meta_data_per_topic = meta_data.get(i);
                    // PostMetadataEntity metaData = meta_data_per_topic.get(0);
                    //String topic_name =  metaData.getName();
                    String topic_id =  ids.get(i);
                    topic_data specificRow = getByIdFromTopicsCache(topic_id);
                    if (specificRow == null)
                        addNewRow(response.isMost_viewed(), 2, topic_id, meta_data_per_topic);
                    else {
                        updateExistedRow(specificRow, response.isMost_viewed(), meta_data_per_topic, 2);
                    }
                }
            }
        }
    }

    private void addNewRow(boolean most_view_Or_recent, int type, String id, List<PostMetadataEntity> meta_data_per_category){
        if(most_view_Or_recent){
            switch (type) {
                case 0 -> insertDataToCategoryCache(id, meta_data_per_category, null);
                case 1 -> insertDataToSubCategoryCache(id, meta_data_per_category, null);
                case 2 -> insertDataToTopicCache(id, meta_data_per_category, null);
            }
        }
        else{
            switch (type) {
                case 0 -> insertDataToCategoryCache(id, null, meta_data_per_category);
                case 1 -> insertDataToSubCategoryCache(id, null, meta_data_per_category);
                case 2 -> insertDataToTopicCache(id, null, meta_data_per_category);
            }
        }
    }

    private void updateExistedRow(Object rowToBeUpdated, boolean whichField, List<PostMetadataEntity> updatedValue, int type){
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

    public List<List<PostMetadataEntity>> validating_number_of_posts(List<List<PostMetadataEntity>> meta_data, int number_of_posts){
        for (int i =0; i<meta_data.size();i++) {
            List<PostMetadataEntity> eachMetaData = meta_data.get(i);
            if (eachMetaData.size() <= number_of_posts)
                return meta_data;
            else {
                for (int j = number_of_posts; j < eachMetaData.size(); j++) {
                    eachMetaData.remove(j);
                    j--;
                }
                meta_data.set(i,eachMetaData);
            }
        }
        return meta_data;
    }




    public List<category_data> getAllDataCategoryCache() {
        return (List<category_data>) CR.findAll();
    }
    public List<sub_category_data> getAllDataSubCategoryCache() {
        return (List<sub_category_data>) SCR.findAll();
    }

    public List<topic_data> getAllDataTopicCache() {
        return (List<topic_data>) TD.findAll();
    }


}
