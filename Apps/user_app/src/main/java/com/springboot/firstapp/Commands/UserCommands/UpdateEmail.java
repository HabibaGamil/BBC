package com.springboot.firstapp.Commands.UserCommands;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.firstapp.Model.User;

import jakarta.servlet.ServletException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class UpdateEmail extends UserCommands{

	public String execute(Map<String, Object> body, 
			  			  Map<String, Object>parameters, 
			  			  Map<String, String>header) 
			  			throws NumberFormatException, ServletException, IOException {

			String filterMsg = doFilter("user", parameters, header);
			if(!filterMsg.equals("You can Pass"))
				return filterMsg;
		
			return updateEmail(Long.parseLong(parameters.get("user_id").toString()), 
						 	   parameters.get("newEmail").toString());

	}
	
	@Transactional
	public String updateEmail(Long id, String newEmail) {
		
		authServiceImpl.printCache();

		if(newEmail.equals(""))
			return "Please, enter your email";
		
		//if the mail format is wrong
		if (!newEmail.matches("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+")) 
            return "Email is not in the correct format";
		
		User user = userRepository.findById(id).orElse(null);
		if(user != null && newEmail.equals(user.getEmail()))
			return "Please, Enter a new email, this is the old email";
		
		Optional<User> existingUser = userRepository.findByEmail(newEmail);
		if(existingUser.isPresent())
			return "this mail is already used";
		
		cachedUserRepository.deleteCachedUser(user.getEmail());
		user.setEmail(newEmail);
		userRepository.save(user);

		//System.out.println(cachedUser);
		System.out.println("fooooooooooooq");
		String accessToken = jwtService.generateAccessToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);
		authServiceImpl.saveToken(accessToken, refreshToken, user);
		
		return "New email saved."+"\n"+"Remember to use it next time you sign in."+"\n"+
								"your new accessToken "+accessToken + 
								"\n" + "your new refreshToken "+refreshToken;
	}
	
}
