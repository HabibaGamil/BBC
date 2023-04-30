package com.example.post_mongo;


//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;
//import org.apache.tomcat.util.json.JSONParser;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class PostMongoApplication {


	public static void main(String[] args)throws Exception {
		SpringApplication.run(PostMongoApplication.class, args);




//		JSONParser parser = new JSONParser();
//		try {
//			Object obj = parser.parse(new FileReader("E:\\SEM 10\\scalable\\post_mongo\\src\\main\\resources\\articleDoc.json"));
//			// A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
//			//JSONObject json = (JSONObject) obj;
//			// A JSON array. JSONObject supports java.util.List interface.
//			//JSONArray companyList = (JSONArray) json.get("Articles");
//			JSONArray array = new JSONArray();
//			array.add(obj);
//
//
//			Iterator<JSONArray> iterator = array.iterator();
//			while (iterator.hasNext()) {
//				counter++;
//				System.out.println(iterator.next());
//				System.out.println();
//			}
//			System.out.println(counter);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

//		File file = new File("E:\\SEM 10\\scalable\\post_mongo\\src\\main\\resources\\articleDoc.json");
//		InputStream inputStream = new FileInputStream(file);
//		JSONTokener tokener = new JSONTokener(inputStream);
//		JSONObject jsonObject = new JSONObject(tokener);
//		JSONArray jsonArray = jsonObject.getJSONArray("Article");
//
//		for (int i = 0; i < jsonArray.length(); i++) {
//			JSONObject item = jsonArray.getJSONObject(i);
//			System.out.println(item);
//		//	JSONArray jsArray = new JSONArray(item.getJSONArray("sub_category"));
//		//	System.out.println(jsArray.get(0));
//			System.out.println(item.getJSONArray("sub_category").get(0));
//
////			String name = item.getString("name");
////			int age = item.getInt("age");
////			System.out.println(name + " is " + age + " years old.");
//			c++;
//		}
//		System.out.println(c);
	}



}
