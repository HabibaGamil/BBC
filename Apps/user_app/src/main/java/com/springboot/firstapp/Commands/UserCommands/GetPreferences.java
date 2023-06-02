package com.springboot.firstapp.Commands.UserCommands;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.springboot.firstapp.Model.Category;
import com.springboot.firstapp.Model.User;

import jakarta.servlet.ServletException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class GetPreferences extends UserCommands{

	public String execute(Map<String, Object> body, 
			  			  Map<String, Object>parameters, 
			  			  Map<String, String>header) 
		throws NumberFormatException, ServletException, IOException {

		String filterMsg = doFilter("user", parameters, header);
		if(!filterMsg.equals("You can Pass"))
				return filterMsg;
		
		return getPreferences(Long.parseLong(parameters.get("user_id").toString()));

	}
	
	public String getPreferences(Long user_id){
		
		User user = userRepository.findById(user_id).orElse(null);
		if(user == null)
			return "No added preferences";
		
		Set<String> prefs = new HashSet<>();
		for(Category category : user.getPreferences())
			prefs.add(category.getName());
		return prefs.toString();
		
	}
}
