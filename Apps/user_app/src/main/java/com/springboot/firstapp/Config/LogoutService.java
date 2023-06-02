package com.springboot.firstapp.Config;

import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.ui.context.Theme;

import com.springboot.firstapp.Model.CachedUser;
import com.springboot.firstapp.Model.User;
import com.springboot.firstapp.Repository.CachedUserRepository;
import com.springboot.firstapp.Repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler{
	
	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final CachedUserRepository cachedUserRepository;
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		
		jwt = authHeader.substring(7);
			
		if(jwtService.isTokenExpired(jwt)) {
			System.out.println("token is expired");
		    return ;
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
				System.out.println("token is invalid *");
				return;
			}
			
			    //delete the user from the cache
				cachedUserRepository.deleteCachedUser(userEmail);
				System.out.println("logged out successfully");
		}
		
		//in that case, the given token is not found in the cache
		else {
			System.out.println("you have to login");
		}
	}

	
	
}
