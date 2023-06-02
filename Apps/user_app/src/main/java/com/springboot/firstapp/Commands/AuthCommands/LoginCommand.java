package com.springboot.firstapp.Commands.AuthCommands;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.firstapp.Model.CachedUser;
import com.springboot.firstapp.Model.User;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LoginCommand extends AuthCommands{

	
	/**************************************execute*******************************************************/
	public String execute(Map<String, Object> body, 
			  Map<String, Object>parameters, 
			  Map<String, String>header) {

			
			return login(parameters.get("email").toString(), 
						 parameters.get("password").toString());

	}
	
	
	/**************************************business logic*************************************************/
	
	@Transactional
	public String login(String email, String password) {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (Exception e) {
			return "Wrong credentials ";
		}
		CachedUser cachedUser = cachedUserRepository.findCachedUserByEmail(email);
				
		//printCache();
		
		//we will generate a new token in two cases
		//1-if the token is already expired
		//2-the token is not expired, but the user performed logged out, so token.isEpired() = true
		String accesstoken = "";
		String refreshToken = "";
		if(cachedUser == null) {
			User user = userRepository.findByEmail(email).orElseThrow();
			accesstoken = jwtService.generateAccessToken(user); 
			refreshToken = jwtService.generateRefreshToken(user);
					cachedUser = CachedUser.builder()
									.userEmail(email)
									.userDetails(user)
									.accessToken(accesstoken)
									.refreshToken(refreshToken)
									.build();
			cachedUserRepository.save(cachedUser);
			return "Successfull login"+"\n"+"accessToken : "+accesstoken + "\n" +"refreshToken : " + refreshToken;
		}
		
		//the user did not log out, however the stored token is expired
		if(cachedUser != null ){
			
			if(!jwtService.isTokenExpired(cachedUser.getAccessToken()))
					return "you are already logged in";
			if(!jwtService.isTokenExpired(cachedUser.getRefreshToken()))
				return "you have to refresh your access Token";
			
			accesstoken = jwtService.generateAccessToken(cachedUser.getUserDetails());
			refreshToken = jwtService.generateRefreshToken(cachedUser.getUserDetails());
			cachedUser.setAccessToken(accesstoken);
			cachedUser.setRefreshToken(refreshToken);
			cachedUserRepository.save(cachedUser);
			System.out.println("********");
			printCache();
			System.out.println("********");
			return"Successfull login"+"\n"+"accessToken : "+accesstoken + "\n" +"refreshToken : " + refreshToken;
		}
		
		
			
			return "you're already logged in";
	}
	
	
	
}
