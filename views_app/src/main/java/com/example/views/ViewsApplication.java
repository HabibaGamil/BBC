package com.example.views;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.swing.text.View;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class ViewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ViewsApplication.class, args);
	}
//	@Bean
//	CommandLineRunner runner(ViewsRepository repository, MongoTemplate mongoTemplate){
//		return args->{
//			String postID = "2";
//			Views newView = new Views(postID,1);
////			Query query = new Query();
////			query.addCriteria(Criteria.where("postID").is(postID));
////			List<Views> foundByID = mongoTemplate.find(query, Views.class);
////			if(foundByID.size()>1){
////               throw new IllegalStateException();
////			}
//		 repository.findViewsByPostID(postID).ifPresentOrElse(v->{
//			 System.out.println(v+"already exists");
//		 },()->{
//		 	repository.insert(newView);
//		 });
//
//
//		};
//
//	}

}



