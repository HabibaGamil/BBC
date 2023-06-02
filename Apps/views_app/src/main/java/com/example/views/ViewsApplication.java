package com.example.views;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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



