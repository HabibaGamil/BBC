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
public class DeletePreference extends UserCommands{
	
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
		
		return deletePreference(Long.parseLong(parameters.get("user_id").toString()), 
			   			   	    Integer.parseInt(parameters.get("pref_id").toString()));
	}
	
	public String deletePreference(Long user_id, int pref_id) {
		
		User user = userRepository.findById(user_id).orElse(null);
		Category category = categoryRepository.findById(pref_id).orElse(null);
		
		if(user == null)
			return "user does not exist";
		if(category == null)
			return "category does not exist";
		
		if(!containsPref(user, pref_id))
			return "you do not have this preference";
		
		deleteUserPref(user, pref_id);
		deleteCategoryUser(category, user_id);
		
		publishToQueue(user);
		
		return "deleted successfully";
	}
	
	public void deleteUserPref(User user, int id) {
		for(Category category : user.getPreferences()) {
			if(category.getId() == id) {
				user.getPreferences().remove(category);
				userRepository.save(user);
				break;
			}
		}
	}
	
	public void deleteCategoryUser(Category category, Long id) {
		for(User user : category.getUsers())
			if(user.getId() == id) {
				category.getUsers().remove(user);
				categoryRepository.save(category);
				break;
			}
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
