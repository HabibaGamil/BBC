package com.springboot.firstapp.Commands.UserCommands;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.firstapp.Model.User;

import jakarta.servlet.ServletException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class UpdateUsername extends UserCommands{

	
	public String execute(Map<String, Object> body, 
			  			  Map<String, Object>parameters, 
			  			  Map<String, String>header) 
			  		throws NumberFormatException, ServletException, IOException {

			String filterMsg = doFilter("user", parameters, header);
			if(!filterMsg.equals("You can Pass"))
				return filterMsg;
		
			return updateUsername(Long.parseLong(parameters.get("user_id").toString()), 
						 		  parameters.get("newUsername").toString());

	}
	
	@Transactional
	public String updateUsername(Long id, String newUsername) {
		
		User user = userRepository.findById(id).orElse(null);
		if(user == null)
			return "user does not exist";
		if(newUsername.equals(""))
			return "user name field can not be empty";
		if(newUsername.equals(user.getUser_name()))
			return "Please, enter a new user name";
		
		user.setUser_name(newUsername);
		userRepository.save(user);
		//CachedUser cachedUser = cachedUserRepository.findCachedUserByEmail(user.getEmail());
		//cachedUser.setUserDetails(user);
		
		return "user name updated successfully";
	}

	
}
