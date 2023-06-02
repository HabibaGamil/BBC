package com.springboot.firstapp.Commands.AuthCommands;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.security.core.Authentication;

import com.springboot.firstapp.Model.CachedUser;
import com.springboot.firstapp.Model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LogoutCommand extends AuthCommands{

	
	
	public String execute(Map<String, Object> body, 
						  Map<String, Object>parameters, 
						  Map<String, String>header) {
			   				
			return logout(header);

}
	
	
	
	public String logout(Map<String, String>header) {
			
			final String authHeader = header.get("authorization");
			final String jwt;
			if(authHeader == null || !authHeader.startsWith("Bearer ")) {
				return "No Token";
			}
			
			jwt = authHeader.substring(7);
				
			if(jwtService.isTokenExpired(jwt)) {
				return "token is expired";
			}
			String userEmail = jwtService.extractUserName(jwt);
			
			//get the user that has that token from the cache
			CachedUser cachedUser = cachedUserRepository.findCachedUserByEmail(userEmail);
			
			//if the user exits
			if(cachedUser != null) {
				String cachedToken = cachedUser.getAccessToken();
				
				//if the stored token is not equal to the given token, because you may have a valid token and
				//then log out and take a new token that will replace the old one in the cache
				//so I have to check that the given token is the last used token (equal to the one in the cache)
				if(cachedToken == null || !cachedToken.equals(jwt)) {
					return "token is invalid *";
				}
				
				    //delete the user from the cache
					cachedUserRepository.deleteCachedUser(userEmail);
					return "logged out successfully";
			}
			
			//in that case, the given token is not found in the cache
			else 
				return "you have to login";
			
		}


}


