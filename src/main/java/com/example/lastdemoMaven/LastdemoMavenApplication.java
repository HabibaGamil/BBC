package com.example.lastdemoMaven;


import com.example.lastdemoMaven.controller.Post_Controller;
import com.example.lastdemoMaven.controller.Search_Controller;
import com.example.lastdemoMaven.model.meta_data;
import com.example.lastdemoMaven.repository.categories_repository;
import com.example.lastdemoMaven.repository.sub_categories_repository;
import com.example.lastdemoMaven.repository.topics_repository;
import com.example.lastdemoMaven.service.SearchService;
import com.example.lastdemoMaven.service.post_service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableRedisRepositories
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

	public static void runService() {
		Post_Controller pc = new Post_Controller();
		Search_Controller sc = new Search_Controller(myService,cd,scr,tr);
//		String [] array1 = new String[]{"12"};
//		String [] array2 = new String[]{"netflix"};

//
//		meta_data m1 = new meta_data("12","ma","123","2",array1,array2,"1/12","12","news");
//		meta_data m2 = new meta_data("13","ma","123","2",array1,array2,"1/12","12","news");
//		meta_data m3 = new meta_data("14","ma","123","2",array1,array2,"1/12","12","news");
//
//		meta_data m4 = new meta_data("15","ma","123","2",array1,array2,"1/12","12","news");
//		meta_data m5 = new meta_data("16","ma","123","2",array1,array2,"1/12","12","news");
//
//		meta_data newest = new meta_data("16news","ma","123","2",array1,array2,"1/12","12","news");
//		meta_data newest2 = new meta_data("17news","ma","123","2",array1,array2,"1/12","12","news");
//
//		meta_data newest3 = new meta_data("18news","ma","123","2",array1,array2,"1/12","12","news");
//		meta_data newest4 = new meta_data("19news","ma","123","2",array1,array2,"1/12","12","news");

//		pc.addPostToTheHash(m1);
//		pc.addPostToTheHash(m2);
//		pc.addPostToTheHash(m3);
//		pc.addPostToTheHash(m4);
//		pc.addPostToTheHash(m5);
// 	pc.addPostToTheHash(newest);
//		pc.addPostToTheHash(newest2);
//		pc.addPostToTheHash(newest3);
//		pc.addPostToTheHash(newest4);
		//System.out.println(pc.getPostHash());
		//pc.deleteTheHash();
	}

	public static void main(String[] args) {
		SpringApplication.run(LastdemoMavenApplication.class, args);
		runService();
		System.out.println("service is running");
	}
}
