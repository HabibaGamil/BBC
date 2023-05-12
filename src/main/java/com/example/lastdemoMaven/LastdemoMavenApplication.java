package com.example.lastdemoMaven;


import com.example.lastdemoMaven.config.RedisConfig;
import com.example.lastdemoMaven.controller.Post_Controller;
import com.example.lastdemoMaven.controller.Search_Controller;
import com.example.lastdemoMaven.model.hash_key;
import com.example.lastdemoMaven.model.meta_data;
import com.example.lastdemoMaven.repository.categories_repository;
import com.example.lastdemoMaven.repository.sub_categories_repository;
import com.example.lastdemoMaven.repository.topics_repository;
import com.example.lastdemoMaven.service.SearchService;
import com.example.lastdemoMaven.service.post_service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class LastdemoMavenApplication {

	private static SearchService myService;
	private static categories_repository cd;
	private  static sub_categories_repository scr;
	private  static topics_repository tr;

	private static post_service ps;

	/*
  JSONObject that will come from response
      "article_id":"",
    "title":"",
    "summary":"",
    "image_id":"",
    "category_ids": [], keywords : [],
    "date":"",
    "view_count": ""*/

	public LastdemoMavenApplication(categories_repository cd,sub_categories_repository scr,topics_repository tr){
		this.cd = cd;
		this.scr = scr;
		this.tr = tr;

		myService = new SearchService(this.cd,this.scr,this.tr);

	}

	public static void runService(){
		Post_Controller pc = new Post_Controller();
		Search_Controller sc = new Search_Controller(myService,cd,scr,tr);
		System.out.println(pc.getPostHash());
	}

	public static void main(String[] args) {
		SpringApplication.run(LastdemoMavenApplication.class, args);
		runService();
		System.out.println("service is running");
	}
}
