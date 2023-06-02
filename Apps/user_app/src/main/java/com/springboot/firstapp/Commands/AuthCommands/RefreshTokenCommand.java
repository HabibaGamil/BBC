package com.springboot.firstapp.Commands.AuthCommands;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import com.springboot.firstapp.Model.CachedUser;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RefreshTokenCommand extends AuthCommands{

	
	
	public String execute(Map<String, Object> body, 
			  			  Map<String, Object>parameters, 
			  			  Map<String, String>header) {

			return refreshToken(parameters.get("refresh").toString());
		
	}
	
	public String refreshToken(String refreshToken) {
		
		final String userEmail;
		if(refreshToken == "") {
			return "no token";
		}
		
		System.out.println("goaaaaaaaaaaaaaaa");
		
		// if the refresh token is expired, then the user needs to login again
		if(jwtService.isTokenExpired(refreshToken))
			return "Token is expired";
		
		//extract the user email from that token 
		userEmail = jwtService.extractUserName(refreshToken);
		
		//if the token contains a user email
		if(userEmail != null) {
			
			CachedUser cachedUser = cachedUserRepository.findCachedUserByEmail(userEmail);
			
			//if the refresh token does not exist in the cache
			if(cachedUser == null) {
				return "invalid token";
			}
			
			//get the userDetails object from the cache
			UserDetails userDetails = cachedUser.getUserDetails();
					
			//get the access token from the cache
			String accessToken = cachedUser.getAccessToken();
			if(!jwtService.isTokenExpired(accessToken))
				return "you can use your access token, as it is not expired";
			
			//We have to check that the token is a valid token and not expired, 
			//and also the given token is equivalent to the token found in the cache
			if(jwtService.isValidToken(refreshToken, userDetails) && refreshToken.equals(cachedUser.getRefreshToken())) {
				String newAccessToken = jwtService.generateAccessToken(userDetails);
				cachedUser.setAccessToken(newAccessToken);
				cachedUserRepository.save(cachedUser);
				return "new AccessToken : "+ newAccessToken + "\n" + "refreshToken : "+ refreshToken;
			}
			
		}	
				
		return "token is invalid, it does not contain email";
		
	}
	
	
	
}
