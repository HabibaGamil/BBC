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
public class DeleteAccount extends UserCommands{

	@Transactional
	public String execute(Map<String, Object> body, 
						  Map<String, Object>parameters, 
						  Map<String, String>header) 
					  throws NumberFormatException, ServletException, IOException {

		String filterMsg = doFilter("user", parameters, header);
		if(!filterMsg.equals("You can Pass"))
			return filterMsg;
		
		return deleteAccount(Long.parseLong(parameters.get("user_id").toString()));
	}
	
	@Transactional
	public String deleteAccount(Long user_id) {
		User user = userRepository.findById(user_id).orElse(null);
		if(user == null)
			return "user does not exist";
		
		user.getPreferences().clear();
		userRepository.save(user);
		
		cachedUserRepository.deleteCachedUser(user.getEmail());
		userRepository.deleteById(user_id);
		
		return "deleted successfully";
	}
}
