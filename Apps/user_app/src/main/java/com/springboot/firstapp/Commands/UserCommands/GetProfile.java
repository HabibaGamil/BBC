package com.springboot.firstapp.Commands.UserCommands;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.springboot.firstapp.Model.User;

import jakarta.servlet.ServletException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class GetProfile extends UserCommands{

	public String execute(Map<String, Object> body, 
			  			  Map<String, Object>parameters, 
			  			  Map<String, String>header) 
			  		throws NumberFormatException, ServletException, IOException {

		String filterMsg = doFilter("user", parameters, header);
		if(!filterMsg.equals("You can Pass"))
			return filterMsg;
		
		System.out.println("laaaaaaaaaaaaaaaaaaaaaaaaaaaaa");;
		return getMyProfile(Long.parseLong(parameters.get("user_id").toString()));

	}
	
	public String getMyProfile(Long user_id) {
		
		User user = userRepository.findById(user_id).orElse(null);
		return toString(user);
	}
	
	public String toString(User user) {
		return "Username : "+ user.getUser_name() + ","
				+ "Email : "+ user.getEmail() + ","
				+ "Country : "+ user.getCountry() + ","
				+ "Birthdate : "+ user.getDate();
	}
}
