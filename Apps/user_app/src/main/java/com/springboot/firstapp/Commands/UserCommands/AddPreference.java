package com.springboot.firstapp.Commands.UserCommands;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.firstapp.DataClasses.UserPrefs;
import com.springboot.firstapp.Model.Category;
import com.springboot.firstapp.Model.User;
import com.springboot.firstapp.RabbitMQ.RabbitMQConfig;

import jakarta.servlet.ServletException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class AddPreference extends UserCommands{

	@Autowired
	RabbitTemplate rabbitTemplate;
	
    @Autowired 
    RabbitMQConfig rabbitMQConfig;
    
    @Value("${rabbitmq.user_newsfeed_queue.queue_name}")
    private String QUEUE_STRING ;
    
    @Value("${rabbitmq.user_newsfeed_queue.exchange_name}")
    private String EXCHANGE_STRING;
    
    @Value("${rabbitmq.user_newsfeed_queue.routing_key}")
    private String ROUTING_KEY_STRING;
	
	@Transactional
	public String execute(Map<String, Object> body, 
						  Map<String, Object>parameters, 
						  Map<String, String>header) 
					  throws NumberFormatException, ServletException, IOException {

		String filterMsg = doFilter("user", parameters, header);
		if(!filterMsg.equals("You can Pass"))
			return filterMsg;
		
		return addPreference(Long.parseLong(parameters.get("user_id").toString()), 
			   			   	 Integer.parseInt(parameters.get("pref_id").toString()));
	}
	
	@Transactional
	public String addPreference(Long user_id, int pref_id) {

		User user = userRepository.findById(user_id).orElse(null);
		Category category = categoryRepository.findById(pref_id).orElse(null);
		System.out.println(user.getPreferences().toArray().toString());
		
		if(user == null)
			return "user does not exist";
		if(category == null)
			return "category does not exist";
		if(containsPref(user,category.getId()))
			return "you already added this category before";
		
		user.getPreferences().add(category);
		userRepository.save(user);
		category.getUsers().add(user);
		categoryRepository.save(category);
		
		publishToQueue(user);
		
		System.out.println(user.getPreferences().toString());
		System.out.println(category.getUsers().toString());
		return "added successfully";
	}
	
	public boolean containsPref(User user, int id) {
		for(Category category : user.getPreferences())
			if(category.getId() == id)
				return true;
		return false;
	}
	
	public void publishToQueue(User user) {
		Set<Integer> prefSet = new HashSet<>();
		for(Category category : user.getPreferences())
			prefSet.add(category.getId());
		
		UserPrefs userPrefs = UserPrefs.builder()
										.id(user.getId())
										.prefSet(prefSet)
										.build();
		
		rabbitTemplate.convertAndSend(EXCHANGE_STRING, 
									  ROUTING_KEY_STRING,
									  userPrefs);
	}
	
}
