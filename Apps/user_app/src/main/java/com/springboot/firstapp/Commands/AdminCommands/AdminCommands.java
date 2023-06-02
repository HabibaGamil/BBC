package com.springboot.firstapp.Commands.AdminCommands;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.springboot.firstapp.Config.JwtService;
import com.springboot.firstapp.Model.CachedUser;
import com.springboot.firstapp.Model.User;
import com.springboot.firstapp.Repository.CachedUserRepository;
import com.springboot.firstapp.Repository.CategoryRepository;
import com.springboot.firstapp.Repository.UserRepository;

import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminCommands {

	@Autowired
	protected CategoryRepository categoryRepository;
	
	@Autowired
	protected UserRepository userRepository;
	
	@Autowired
	protected CachedUserRepository cachedUserRepository;
	
	@Autowired
	protected JwtService jwtService;
	
	protected String doFilter(String expectedRole,
			  				  Map<String, Object>parameters, 
			  				  Map<String, String>header)
			  						  throws ServletException, IOException {

			//extract the header
			final String authHeader = header.get("authorization");
			
			final String jwt;
			final String userEmail;
			if(authHeader == null || !authHeader.startsWith("Bearer ")) 
			return "Token is Required";
			
			
			//extract the jwt token
			jwt = authHeader.substring(7);
			
			//if the token is expired
			if(jwtService.isTokenExpired(jwt))
			return "Access Token is expired, you can use your refresh token to get a new one";
			
			//extract the user email from that token 
			userEmail = jwtService.extractUserName(jwt);
			
			
			//if the token contains a user email
			if(userEmail != null) {
			
			CachedUser cachedUser = cachedUserRepository.findCachedUserByEmail(userEmail);
			System.out.println(cachedUser);
			
			//if this token is not the cache, then the user need to login again
			if(cachedUser == null) 
				return "This token represents email that does not exist in the cache, you may need to login";
			
			//get the userDetails object from the cache
			UserDetails userDetails = cachedUser.getUserDetails();
			
			System.out.println("-------> : "+((User)(userDetails)).isAdmin());
			
			
			/****************only admin*********************/
			if(userDetails != null && !(((User) userDetails).isAdmin()) && expectedRole.equals("admin")) { 
				System.out.println((((User) userDetails).isAdmin()));
				return "only admin";
			}
			/**************************************/
			
			/*************************added section***************************************/
			if(parameters.get("user_id") != null) {
			final Long id = Long.parseLong(parameters.get("user_id").toString());
			if(id != ((User) userDetails).getId()) {
				return "you are not authorized";
			}
			}
			/**************************************************************************/	
			
			//get the token from the cache
			String cachedToken = cachedUser.getAccessToken();
			
			//We have to check that the token is a valid token and not expired, 
			//and also the given token is equivalent to the token found in the cache
			if(jwtService.isValidToken(jwt, userDetails) && 
				cachedToken != null && 
				cachedToken.equals(jwt)) {
			return "You can Pass";
			}
			
			}
			
			//token does not have email
			return "Invalid token";
} 


	
}
