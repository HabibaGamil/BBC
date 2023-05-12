package com.example.lastdemoMaven;


import com.example.lastdemoMaven.config.RedisConfig;
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

	public static void main(String[] args) {
		SpringApplication.run(LastdemoMavenApplication.class, args);
//		String[] array12 = new String[] {"312"};
//		String[] array1 = new String[] {"news"};
//		RedisConfig rc = new RedisConfig();
//		meta_data m = new meta_data("12","ma","123","2",array12,array1,"123","2","news");
//		meta_data c = new meta_data("12","ma","123","2",array12,array1,"123","2","news");
//		Map<String,Object> mmd= new HashMap<>();
//		mmd.put((new hash_key("meta")).getHash_key(),m);
//		mmd.put((new hash_key("meta2")).getHash_key(),c);
//		RedisTemplate<String, Object> rt =  rc.template();
//		post_service ps = new post_service(rt);
//		ps.addToHashThePosts("hash",mmd);
//		System.out.println(ps.getHash("hash"));

//		String[] array12 = new String[] {"312"};
//		String[] array1 = new String[] {"news"};
//		List<meta_data [] > md = new ArrayList<>();
//		meta_data m = new meta_data("12","ma","123","2",array12,array1,"123","2","news");
//		meta_data[] me = new meta_data[] {m};
//		md.add(me);
//		SearchResponse sr = new SearchResponse(false,0,array12,4,md);
//		Search_Controller sc = new Search_Controller(myService,cd,scr,tr);
//		sc.process_search_response(sr);
		//myService.update_cache(sr);
		//System.out.println("added successfully");
//		System.out.println(myService.getByIdFromCategoryCache("312"));


//		String key = "posts_hash";
//		Map<String,meta_data> mmd= new HashMap<>();
//		mmd.put("news",m);
//		System.out.println("added");



		//List<meta_data> h = new ArrayList<>();
		//h.add(m);
		//postService.insertIntoCache(new post_data("news",h));
		//System.out.println("data added successfully");
//		postService.deleteSpecificPostById("news");
		//System.out.println((postService.getSpecificPostById("news")));
//		String[] array12 = new String[] {"3"};
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("category_name", "football");
//		jsonObject.put("category_ids", array12);
//		JSONObject [] tryTh = new JSONObject[1];
//		tryTh[0] = jsonObject;
//		List<JSONObject[]> list = new ArrayList<>();
//		list.add(tryTh);
//		SearchResponse sr = new SearchResponse(true,0,array12,4,list);
//		//myService.update_cache(sr);
//		//System.out.println("data inserted successfully");
//		category_data c_d = myService.getByIdFromCategoryCache("football");
//		System.out.println(c_d.getMost_viewed());
//		System.out.println(c_d.getMost_recent());
//		System.out.println("data inserted successfully");
	}
}
