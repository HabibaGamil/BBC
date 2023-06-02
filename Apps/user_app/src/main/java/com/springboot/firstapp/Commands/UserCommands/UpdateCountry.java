package com.springboot.firstapp.Commands.UserCommands;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netflix.discovery.EurekaUpStatusResolver;
import com.springboot.firstapp.Model.User;

import jakarta.servlet.ServletException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class UpdateCountry extends UserCommands{

	public String execute(Map<String, Object> body, 
			  			  Map<String, Object>parameters, 
			  			  Map<String, String>header) 
			throws NumberFormatException, ServletException, IOException {

			String filterMsg = doFilter("user", parameters, header);
			if(!filterMsg.equals("You can Pass"))
				return filterMsg;

			return updateCountry(Long.parseLong(parameters.get("user_id").toString()), 
			 	   			   	 parameters.get("newCountry").toString());

	}
	
	@Transactional
	public String updateCountry(Long id, String newCountry){
		
		//get the user that has the passed id
		User user = userRepository.findById(id).orElse(null);
		//the user does not exist
		if(user == null)
			return "user does not exist";
		//the user exists, however the field of country is empty
		if(newCountry.equals(""))
			return "Please, enter the country name";
		//the country name is incorrect
		if(!COUNTRIES_HASH_SET.contains(newCountry.toLowerCase()))
			return "Please, enter a vaild country name";
		
		user.setCountry(newCountry.toLowerCase());
		userRepository.save(user);
		//CachedUser cachedUser = cachedUserRepository.findCachedUserByEmail(user.getEmail());
		//cachedUser.setUserDetails(user);
		
		return "Country Updated successfully";
	}
	
	
}
