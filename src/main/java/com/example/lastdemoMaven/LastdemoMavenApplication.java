package com.example.lastdemoMaven;


import com.example.lastdemoMaven.controller.Post_Controller;
import com.example.lastdemoMaven.controller.Search_Controller;
import com.example.lastdemoMaven.dbo.SearchRequest;
import com.example.lastdemoMaven.dbo.SearchResponse;
import com.example.lastdemoMaven.model.PostMetadataEntity;
import com.example.lastdemoMaven.repository.categories_repository;
import com.example.lastdemoMaven.repository.sub_categories_repository;
import com.example.lastdemoMaven.repository.topics_repository;
import com.example.lastdemoMaven.service.SearchService;
import com.example.lastdemoMaven.service.post_service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.*;

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


	/*      private String postMetadataId;
    private String title;
    private String summary;
    private String imageId;
    private List<String> categoryIds;
    private List<String> subcategoryIds;
    private String topicId;
    private List<String> keywords;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date createdAt;
    private long viewCount;
    */

	public LastdemoMavenApplication(categories_repository cd,sub_categories_repository scr,topics_repository tr){
		this.cd = cd;
		this.scr = scr;
		this.tr = tr;

		myService = new SearchService(this.cd,this.scr,this.tr);

	}

	public static void runService() {
		Post_Controller pc = new Post_Controller();
		Search_Controller sc = new Search_Controller(myService,cd,scr,tr);
//		List<PostMetadataEntity> lmd = new ArrayList<>();
////		List<PostMetadataEntity> lmd1 = new ArrayList<>();
////		List<PostMetadataEntity> lmd2 = new ArrayList<>();
//
//		List<List<PostMetadataEntity>> m = new ArrayList<>();
//
//		List<String> array1 = new ArrayList<String>();
//		array1.add("12");
//		List<String> array2 = new ArrayList<>() ;
//		array2.add("netflix");
//
//
//		PostMetadataEntity m1 = new PostMetadataEntity("12","ma","123","2",array1,array2,"",array1,null,12);
//		PostMetadataEntity m2 =  new PostMetadataEntity("13","ma","123","2",array1,array2,"",array1,null,12);
//		PostMetadataEntity m3 =  new PostMetadataEntity("14","ma","123","2",array1,array2,"",array1,null,12);
//		lmd.add(m1);
//		lmd.add(m2);
//		lmd.add(m3);
//		m.add(lmd);
//
//		List<String> ids = new ArrayList<>();
//		ids.add("2000");
//		ids.add("2001");
////
////   		SearchResponse sr = new SearchResponse(true,0,ids,10,m);
////		sc.process_search_response(sr);
//		System.out.println(sc.get("2001"));
////		System.out.println(m);
//		//System.out.println(sc.validate(m,1));




//		List<String> array1 = new ArrayList<String>();
//		array1.add("12");
//		List<String> array2 = new ArrayList<>() ;
//		array2.add("netflix");
//
//		PostMetadataEntity m4 = new PostMetadataEntity("15","ma","123","2",array1,array2,"",array1,null,12);
//		PostMetadataEntity m5 = new PostMetadataEntity("16","ma","123","2",array1,array2,"",array1,null,12);
//
//		PostMetadataEntity newest = new PostMetadataEntity("17","ma","123","2",array1,array2,"",array1,null,12);
//		PostMetadataEntity newest2 =new PostMetadataEntity("18","ma","123","2",array1,array2,"",array1,null,12);
//
//		PostMetadataEntity newest3 = new PostMetadataEntity("18news","ma","123","2",array1,array2,"1/12","12","news");
//		PostMetadataEntity newest4 = new PostMetadataEntity("19news","ma","123","2",array1,array2,"1/12","12","news");
//
//		pc.addPostToTheHash(m1);
//		pc.addPostToTheHash(m2);
//		pc.addPostToTheHash(m3);
//		pc.addPostToTheHash(m4);
//		pc.addPostToTheHash(m5);
// 	    pc.addPostToTheHash(newest);
//		pc.addPostToTheHash(newest2);
////		pc.addPostToTheHash(newest3);
////		pc.addPostToTheHash(newest4);
//		System.out.println(pc.getPostHash());
		//pc.deleteTheHash();
	}

	public static void main(String[] args) {
		SpringApplication.run(LastdemoMavenApplication.class, args);
		runService();
		System.out.println("service is running");
	}
}
