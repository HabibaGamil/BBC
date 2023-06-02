package com.springboot.firstapp.Commands.UserCommands;

import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import com.springboot.firstapp.Config.JwtService;
import com.springboot.firstapp.Model.CachedUser;
import com.springboot.firstapp.Model.User;
import com.springboot.firstapp.Repository.CachedUserRepository;
import com.springboot.firstapp.Repository.CategoryRepository;
import com.springboot.firstapp.Repository.UserRepository;
import com.springboot.firstapp.Service.Impl.AuthServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCommands {

	@Autowired
	protected UserRepository userRepository;
	
	@Autowired
	protected CategoryRepository categoryRepository;
		
	@Autowired
	protected CachedUserRepository cachedUserRepository;
	
	//private final CacheManager cacheManager;
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Autowired
	protected JwtService jwtService;
	
	@Autowired
	protected AuthenticationManager authenticationManager;
	
	@Autowired
	protected AuthServiceImpl authServiceImpl;
	
	protected static final HashSet<String> COUNTRIES_HASH_SET = getCountries();
	
	private static HashSet<String> getCountries(){
		String[] locales = Locale.getISOCountries();
		HashSet<String> set = new HashSet<>();
		for(String code : locales) {
			set.add(new Locale("",code).getDisplayCountry().toLowerCase());
		}
		return set;
	}


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
			
			/****************only admin*********************/
			if(userDetails != null && !(((User) userDetails).isAdmin()) && expectedRole.equals("admin")) 
				return "only admin";
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
