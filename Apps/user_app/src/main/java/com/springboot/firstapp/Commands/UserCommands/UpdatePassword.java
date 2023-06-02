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
@Transactional
public class UpdatePassword extends UserCommands{

	
	public String execute(Map<String, Object> body, 
			  Map<String, Object>parameters, 
			  Map<String, String>header) throws NumberFormatException, ServletException, IOException {

			String filterMsg = doFilter("user", parameters, header);
			if(!filterMsg.equals("You can Pass"))
				return filterMsg;
		
			return updatePassword(Long.parseLong(parameters.get("user_id").toString()), 
						 		  parameters.get("oldPassword").toString(),
						 		  parameters.get("newPassword").toString());

	}
	
	
	@Transactional
	public String updatePassword(Long id,String oldPassword, String newPassword) {
		/*
		 * 1-check if the user exists
		 * 2-check if old password is not empty
		 * 3-check if old password is correct
		 * 4-check if new password is not empty
		 * 4-encrypt the new password
		 * 5-set the new password
		 * */
		User user = userRepository.findById(id).orElseThrow(
											() -> new IllegalStateException("User does not exist"));
		
		if(oldPassword.equals(""))
			return "Please, enter the old password";

		if(!passwordEncoder.matches(oldPassword+"",user.getPassword())	)
			return "the old password is incorrect ";
		
		if(newPassword.equals(""))
			return "Please, enter the new password";
		
		String encryptedPassword = passwordEncoder.encode(newPassword);
		user.setPassword(encryptedPassword);
		userRepository.save(user);
		
//		CachedUser cachedUser = cachedUserRepository.findCachedUserByEmail(user.getEmail());
//		cachedUser.setUserDetails(user);
			
		return "Password is updated successfully, please remember to use it the next time";
		
	}
}
