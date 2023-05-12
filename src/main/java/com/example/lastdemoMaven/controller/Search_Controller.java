package com.example.lastdemoMaven.controller;


import com.example.lastdemoMaven.dbo.SearchResponse;
import com.example.lastdemoMaven.repository.categories_repository;
import com.example.lastdemoMaven.repository.sub_categories_repository;
import com.example.lastdemoMaven.repository.topics_repository;
import com.example.lastdemoMaven.service.SearchService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Search_Controller {

   private final SearchService search_service;
   private final categories_repository categories_repository;
   private final sub_categories_repository sub_categories_repository;
   private final topics_repository topics_repository;

   public void process_search_response(SearchResponse search_response){
       search_service.update_cache(search_response);
   }
}
